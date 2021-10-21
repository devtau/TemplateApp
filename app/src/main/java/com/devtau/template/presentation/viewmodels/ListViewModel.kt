package com.devtau.template.presentation.viewmodels

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.devtau.template.R
import com.devtau.template.data.Result
import com.devtau.template.data.Result.Success
import com.devtau.template.data.model.Apple
import com.devtau.template.data.source.local.PreferencesManager
import com.devtau.template.data.source.repositories.ApplesRepository
import com.devtau.template.utils.Event
import com.devtau.template.presentation.ResourceResolver
import com.devtau.template.utils.DateUtils
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

/**
 * ViewModel working with list of [Apple]s
 * @param applesRepository repository to work with [Apple] instances
 * @param prefs manager working with list of [SharedPreferences]
 * @param resourceResolver provider of android resources such as strings and colors
 */
class ListViewModel @Inject constructor(
    private val applesRepository: ApplesRepository,
    private val prefs: PreferencesManager,
    private val resourceResolver: ResourceResolver
): BaseViewModel() {

    val apples: LiveData<List<Apple>> = applesRepository.observeList()
        .distinctUntilChanged()
        .switchMap { processModelsFromDB(it) }
    val openAppleDetailsEvent = MutableLiveData<Event<Apple>>()
    private val favoritesFilterActive = MutableLiveData(false)
    val favoritesFilterIcon: LiveData<Drawable?> = favoritesFilterActive.map {
        val favoritesIconId = if (it) {
            R.drawable.ic_favorite_white
        } else {
            R.drawable.ic_favorite_border_white
        }
        resourceResolver.resolveDrawable(favoritesIconId)
    }

    init {
        syncIfNeeded()
    }

    fun addApple() = viewModelScope.launch {
        try {
            progress.value = true
            val newApple = Apple.getMock()
            if (applesRepository.saveItem(newApple) is Success) {
                openAppleDetailsEvent.value = Event(newApple)
            }
        } catch (e: Exception) {
            Timber.e(e)
            error.value = Event(R.string.error)
        } finally {
            progress.value = false
        }
    }

    fun openApple(apple: Apple) = viewModelScope.launch {
        try {
            openAppleDetailsEvent.value = Event(apple)
        } catch (e: Exception) {
            Timber.e(e)
            error.value = Event(R.string.error_on_apple_open)
        } finally {
            progress.value = false
        }
    }

    fun fetchItemsFromBackend() = viewModelScope.launch {
        try {
            progress.value = true
            applesRepository.fetchItemsFromBackend()
            prefs.lastSyncDate = Calendar.getInstance().timeInMillis
        } catch (e: Exception) {
            Timber.e(e)
            error.value = Event(R.string.error_on_apples_fetch)
        } finally {
            progress.value = false
        }
    }

    fun searchQueryEntered(it: String?) {
        Timber.d("searchQueryEntered=$it")
    }

    fun onFavoritesFilterClicked() {
        favoritesFilterActive.value = favoritesFilterActive.value?.not()
        Timber.d("applyFavoritesFilter=${favoritesFilterActive.value}")
    }

    /**
     * We intend to sync only once a day
     */
    private fun syncIfNeeded() {
        if (DateUtils.isDateBeforeYesterday(prefs.lastSyncDate)) fetchItemsFromBackend()
    }

    private fun processModelsFromDB(result: Result<List<Apple>>): LiveData<List<Apple>> =
        if (result is Success) {
            Timber.d("got new apples=${result.data}")
            MutableLiveData(result.data)
        } else {
            MutableLiveData(null)
        }
}