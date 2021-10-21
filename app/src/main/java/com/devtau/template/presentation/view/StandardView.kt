package com.devtau.template.presentation.view

import android.content.Context
import io.reactivex.functions.Action

interface StandardView {
    fun getContext(): Context
    fun showMsg(msgId: Int, confirmedListener: Action? = null, cancelledListener: Action? = null)
    fun showMsg(msg: String, confirmedListener: Action? = null, cancelledListener: Action? = null)
    fun isOnline(): Boolean
}