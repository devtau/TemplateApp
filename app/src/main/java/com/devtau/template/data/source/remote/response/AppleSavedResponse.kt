package com.devtau.template.data.source.remote.response

import com.devtau.template.data.model.Apple

/**
 * Backend response class on [Apple] saved
 */
class AppleSavedResponse: BaseResponse() {

    val apple: Apple? get() = data?.apple
    private val data: Data? = null

    private class Data(val apple: Apple? = null)
}