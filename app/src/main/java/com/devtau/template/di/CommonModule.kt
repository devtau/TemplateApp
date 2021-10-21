package com.devtau.template.di

import com.devtau.template.data.source.local.PreferencesManager
import com.devtau.template.data.source.local.PreferencesManagerImpl
import com.devtau.template.presentation.ResourceResolver
import com.devtau.template.presentation.ResourceResolverImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class CommonModule {

    @Binds
    @Singleton
    abstract fun providePrefs(manager: PreferencesManagerImpl): PreferencesManager

    @Binds
    abstract fun provideResourceResolver(resources: ResourceResolverImpl): ResourceResolver
}