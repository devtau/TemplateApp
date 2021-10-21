package com.devtau.template.data.source.remote.response

import com.devtau.template.data.model.Carrot

/**
 * Backend response class on [Carrot] saved
 */
class CarrotSavedResponse: BaseResponse() {

    val carrot: Carrot? get() = data?.Carrot
    private val data: Data? = null

    private class Data(val Carrot: Carrot? = null)
}