package com.devtau.template.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import javax.inject.Inject

/**
 * Provides ability to get common android resources from within view models
 */
interface ResourceResolver {

    /**
     * Provides ability to get string
     * @param stringResId id of string
     * @return resolved string
     */
    fun resolveString(@StringRes stringResId: Int): String

    /**
     * Provides ability to get color
     * @param colorResId id of color
     * @return resolved color
     */
    fun resolveColor(@ColorRes colorResId: Int): Int

    /**
     * Provides ability to get drawable
     * @param drawableResId id of drawable
     * @return resolved drawable
     */
    fun resolveDrawable(@DrawableRes drawableResId: Int): Drawable?
}

class ResourceResolverImpl @Inject constructor(private val context: Context): ResourceResolver {

    override fun resolveString(
        @StringRes stringResId: Int
    ): String = context.getString(stringResId)

    override fun resolveColor(
        @ColorRes colorResId: Int
    ): Int = context.getColor(colorResId)

    override fun resolveDrawable(
        @DrawableRes drawableResId: Int
    ): Drawable? = ContextCompat.getDrawable(context, drawableResId)
}