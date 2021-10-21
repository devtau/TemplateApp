package com.devtau.template

import android.app.Application
import com.devtau.template.di.AppComponent
import com.devtau.template.di.DaggerAppComponent
import timber.log.Timber

/**
 * Custom application class. Holds dagger generated AppComponent
 */
class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}