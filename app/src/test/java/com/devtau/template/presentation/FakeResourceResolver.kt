package com.devtau.template.presentation

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

object FakeResourceResolver: ResourceResolver {

    override fun resolveString(
        @StringRes stringResId: Int
    ): String = ""

    override fun resolveColor(
        @ColorRes colorResId: Int
    ): Int = 0

    override fun resolveDrawable(
        @DrawableRes drawableResId: Int
    ): Drawable? = null
}