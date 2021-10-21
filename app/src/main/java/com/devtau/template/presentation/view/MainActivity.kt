package com.devtau.template.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.findNavController
import com.devtau.template.R
import com.devtau.template.utils.showSnackBar
import timber.log.Timber

/**
 * Root activity of app. Holds all fragments navigation container
 */
class MainActivity: AppCompatActivity(R.layout.activity_main), BaseFragment.Listener {

    private lateinit var root: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        root = findViewById(R.id.root)
    }

    override fun showDetails(view: View, modelId: Long, toolbarTitle: String) {
        Timber.d("showDetails. modelId=$modelId")
        val direction = ListFragmentDirections.actionListFragmentToDetailsFragment(modelId, toolbarTitle)
        view.findNavController().navigate(direction)
    }

    override fun showSnackBar(msg: Int) = root.showSnackBar(msg)
}