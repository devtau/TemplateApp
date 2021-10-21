package com.devtau.template.di

import android.content.Context
import com.devtau.template.data.source.local.DB
import com.devtau.template.data.source.local.apples.AppleDao
import com.devtau.template.data.source.local.carrots.CarrotDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun getRoomDatabase(context: Context): DB = DB.getInstance(context)

    @Provides
    fun getAppleDao(db: DB): AppleDao = db.appleDao()

    @Provides
    fun getCarrotDao(db: DB): CarrotDao = db.carrotDao()
}