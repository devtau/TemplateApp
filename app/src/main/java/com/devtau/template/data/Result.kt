package com.devtau.template.data

import androidx.annotation.StringRes
import com.devtau.template.R
import com.devtau.template.data.Result.Success

/**
 * Wrapper for models being used as a return type when request status is needed
 */
sealed class Result<out T> {

    data class Success<out T>(
        val data: T
    ): Result<T>()

    data class Error(
        @StringRes val description: Int = R.string.error,
        val exception: Exception? = null
    ): Result<Nothing>()

    object Loading: Result<Nothing>()

    override fun toString(): String = when (this) {
        is Success<*> -> "Success. data=$data"
        is Error -> "Error. exception=$exception"
        is Loading -> "Loading"
    }
}

fun Result<*>.succeeded() = this is Success && data != null