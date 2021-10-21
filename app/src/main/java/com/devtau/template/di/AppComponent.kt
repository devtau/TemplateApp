package com.devtau.template.di

import android.content.Context
import com.devtau.template.presentation.view.DetailsFragment
import com.devtau.template.presentation.view.ListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger graph of dependencies in app
 */
@Singleton
@Component(
    modules = [
        CommonModule::class,
        RoomModule::class,
        RetrofitModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: DetailsFragment)

    fun inject(fragment: ListFragment)
}