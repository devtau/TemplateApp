package com.devtau.template.presentation.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtau.template.utils.Event

/**
 * Regular [ViewModel] with common [LiveData]s to interact with user
 */
abstract class BaseViewModel: ViewModel() {

    /**
     * Indicates to user that viewModel is currently doing some work
     */
    val progress = MutableLiveData<Boolean>()

    /**
     * Indicates necessity to process error (i.e. show info to user).
     * Error message id is wrapped in one shot [Event]
     */
    val error = MutableLiveData<Event<@StringRes Int>>()
}