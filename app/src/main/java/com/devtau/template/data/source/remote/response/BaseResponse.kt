package com.devtau.template.data.source.remote.response

/**
 * General backend response class
 * @param code standard http response or error code
 * @param message description of error if any
 */
abstract class BaseResponse(
    val code: Int = 0,
    val message: String? = null
)