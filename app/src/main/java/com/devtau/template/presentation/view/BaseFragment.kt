package com.devtau.template.presentation.view

import android.content.Context
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

/**
 * Superclass for all fragments in app.
 * LayoutRes is excessive when using data binding
 */
abstract class BaseFragment: Fragment() {

    lateinit var listener: Listener
    val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = when {
            context is Listener -> context
            parentFragment is Listener -> parentFragment as Listener
            else -> throw ClassCastException("$context must implement ${Listener::class.qualifiedName}")
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    interface Listener: Coordinator {
        fun showSnackBar(msg: Int)
    }
}