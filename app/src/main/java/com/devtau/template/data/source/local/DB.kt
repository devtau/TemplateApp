package com.devtau.template.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devtau.template.BuildConfig
import com.devtau.template.data.model.Apple
import com.devtau.template.data.model.Carrot
import com.devtau.template.data.source.local.apples.AppleDao
import com.devtau.template.data.source.local.carrots.CarrotDao

/**
 * Local db provider class
 */
@Database(entities = [Apple::class, Carrot::class], version = 1)
abstract class DB: RoomDatabase() {

    /**
     * Provides DAO for [Apple] instances
     */
    abstract fun appleDao(): AppleDao

    /**
     * Provides DAO for [Carrot] instances
     */
    abstract fun carrotDao(): CarrotDao

    companion object {
        @Volatile private var INSTANCE: DB? = null

        fun getInstance(context: Context): DB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, DB::class.java, BuildConfig.DATABASE_NAME)
                .fallbackToDestructiveMigration().build()
    }
}