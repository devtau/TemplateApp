package com.devtau.template.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.devtau.template.R
import com.devtau.template.data.Result
import com.devtau.template.data.Result.Error
import com.devtau.template.data.Result.Success
import com.devtau.template.data.model.Apple
import com.devtau.template.data.source.repositories.ApplesRepository
import com.devtau.template.presentation.ResourceResolver
import com.devtau.template.utils.Event
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel working with chosen [Apple]
 * @param handle a handle to saved state passed down to ViewModel
 * @param applesRepository repository to work with [Apple] instances
 * @param resourceResolver provider of android resources such as strings and colors
 */
class DetailsViewModel(
    private val handle: SavedStateHandle,
    private val applesRepository: ApplesRepository,
    private val resourceResolver: ResourceResolver
): BaseViewModel() {

    val apple = MutableLiveData<Apple?>()

    private var appleId: Long = handle["modelId"] ?: -1L

    init {
        loadApple()
    }

    fun fetchItemFromBackend() = viewModelScope.launch {
        try {
            applesRepository.fetchItemFromBackend(appleId)
        } catch (e: Exception) {
            Timber.e(e)
            error.value = Event(R.string.error_on_apple_fetch)
        } finally {
            progress.value = false
        }
    }

    private fun loadApple() = viewModelScope.launch {
        apple.value = processModelFromDB(applesRepository.getItem(appleId))
    }

    private fun processModelFromDB(result: Result<Apple?>): Apple? = when (result) {
        is Success -> {
            Timber.d("got new apple=${result.data}")
            result.data
        }
        is Error -> {
            error.value = Event(result.description)
            null
        }
        else -> null
    }
}