package com.devtau.template.utils

import androidx.lifecycle.Observer
import timber.log.Timber

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        Timber.d("getContentIfNotHandled. hasBeenHandled=$hasBeenHandled, content=$content")
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserver<T>(
    private val onEventUnhandledContent: (T) -> Unit
): Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        val content = event?.getContentIfNotHandled()
        if (content == null) {
            Timber.w("onChanged. content is null")
        } else {
            onEventUnhandledContent(content)
        }
    }
}