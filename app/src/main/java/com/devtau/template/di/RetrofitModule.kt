package com.devtau.template.di

import com.devtau.template.data.source.remote.REST
import com.devtau.template.data.source.remote.apples.ApplesBackendApi
import com.devtau.template.data.source.remote.carrots.CarrotsBackendApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit = REST.getInstance()

    @Provides
    @Singleton
    fun getApplesBackendAPI(retrofit: Retrofit): ApplesBackendApi
            = retrofit.create(ApplesBackendApi::class.java)

    @Provides
    @Singleton
    fun getCarrotsBackendAPI(retrofit: Retrofit): CarrotsBackendApi
            = retrofit.create(CarrotsBackendApi::class.java)
}