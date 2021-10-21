package com.devtau.template.presentation.view

import android.view.View
import com.devtau.template.presentation.view.DetailsFragment

/**
 * Manages navigation in app
 */
interface Coordinator {

    /**
     * Navigates to [DetailsFragment]
     * @param view provider of context and NavController
     * @param modelId id of model selected by user
     * @param toolbarTitle title to use in toolbar
     */
    fun showDetails(view: View, modelId: Long, toolbarTitle: String)
}