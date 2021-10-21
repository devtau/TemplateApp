package com.devtau.template.data.source.remote

import com.devtau.template.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Backend API provider class
 */
object REST {

    private const val TIMEOUT_CONNECT = 10L
    private const val TIMEOUT_READ = 60L
    private const val TIMEOUT_WRITE = 120L

    @Volatile private var INSTANCE: Retrofit? = null

    fun getInstance(): Retrofit =
        INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildRetrofit().also {
                INSTANCE = it
            }
        }

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(buildClient())
        .build()

    private fun buildClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder.build()
    }
}