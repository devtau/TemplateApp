package com.devtau.template.ui.fragments.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.devtau.template.MainCoroutineRule
import com.devtau.template.R
import com.devtau.template.data.model.Apple
import com.devtau.template.data.source.BaseDataSource
import com.devtau.template.data.source.FakeApplesDataSource
import com.devtau.template.data.source.FakePreferencesManager
import com.devtau.template.data.source.local.PreferencesManager
import com.devtau.template.data.source.repositories.ApplesRepository
import com.devtau.template.getOrAwaitValue
import com.devtau.template.presentation.FakeResourceResolver
import com.devtau.template.presentation.ResourceResolver
import com.devtau.template.presentation.viewmodels.ListViewModel
import com.devtau.template.utils.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.*
import java.util.Calendar

/**
 * More info on test pairs.
 * See [guide](https://testing.googleblog.com/2013/07/testing-on-toilet-know-your-test-doubles.html).
 * See [tips](https://developer.android.com/training/testing/fundamentals#test-doubles).
 */
@ExperimentalCoroutinesApi
class ListViewModelTest {

    private val fakeLocalList = Apple.getMockLocalList()
    private val fakeRemoteList = Apple.getMockRemoteList()
    private val fakeLocalDataSource: BaseDataSource<Apple> = FakeApplesDataSource(fakeLocalList)
    private val fakeRemoteDataSource: BaseDataSource<Apple> = FakeApplesDataSource(fakeRemoteList)
    private lateinit var fakeRepository: ApplesRepository
    private lateinit var fakePrefs: PreferencesManager
    private lateinit var resourceResolver: ResourceResolver

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeRepository = ApplesRepository(fakeLocalDataSource, fakeRemoteDataSource)
        fakePrefs = FakePreferencesManager
        resourceResolver = FakeResourceResolver
    }

    @Test
    fun getProgress() {
        //Given a fresh ViewModel
        val viewModel = newListViewModel()

        //When showing progress
        viewModel.progress.value = true

        //Then the progress is shown
        val value = viewModel.progress.getOrAwaitValue()
        assert(value)
    }

    @Test
    fun getError() {
        //Given a fresh ViewModel
        val viewModel = newListViewModel()

        //When showing error
        viewModel.error.value = Event(R.string.error)

        //Then the new error event is triggered and consumed
        val value = viewModel.error.getOrAwaitValue()
        assertEquals(R.string.error, value.getContentIfNotHandled())
        assertEquals(null, value.getContentIfNotHandled())
    }

    @Test
    fun getTeams() {
        //Given a fresh ViewModel
        val viewModel = newListViewModel()

        //When observing teams LiveData
        fakeRepository.observeList()

        //Then the teams are being provided from local list
        val value = viewModel.apples.getOrAwaitValue()
        assertEquals(fakeLocalList, value)
    }

    @Test
    fun getOpenTeamDetailsEvent() {
        //Given a fresh ViewModel
        val viewModel = newListViewModel()

        //When opening team details
        viewModel.openAppleDetailsEvent.value = Event(Apple.getMock())

        //Then team details event is triggered and consumed
        val value = viewModel.openAppleDetailsEvent.getOrAwaitValue()
        assertEquals(Apple.getMock(), value.getContentIfNotHandled())
        assertEquals(null, value.getContentIfNotHandled())
    }

    @Test
    fun openTeam() {
        //Given a fresh ViewModel
        val viewModel = newListViewModel()

        //When opening team details
        viewModel.openApple(Apple.getMock())

        //Then team details event is triggered and consumed
        val value = viewModel.openAppleDetailsEvent.getOrAwaitValue()
        assertEquals(Apple.getMock(), value.getContentIfNotHandled())
        assertEquals(null, value.getContentIfNotHandled())
    }

    @Test
    fun fetchItemsFromBackend() {
        //If local and remote lists vary before ViewModel is created
        assertEquals(4, fakeLocalList.size)
        assertEquals(5, fakeRemoteList.size)

        //And there is no need to sync on ViewModel created
        fakePrefs.lastSyncDate = Calendar.getInstance().timeInMillis

        //Then after ViewModel is created
        val viewModel = newListViewModel()

        //Content of lists is not synchronised
        assertEquals(4, fakeLocalList.size)
        assertEquals(5, fakeRemoteList.size)

        //When fetch method is triggered
        viewModel.fetchItemsFromBackend()

        //Then progress event is triggered and set to false
        val value = viewModel.progress.getOrAwaitValue()
        assertEquals(false, value)

        //And content of local list is updated
        assertEquals(5, fakeLocalList.size)
        assertEquals(5, fakeRemoteList.size)
    }

    private fun newListViewModel() = ListViewModel(fakeRepository, fakePrefs, resourceResolver)
}