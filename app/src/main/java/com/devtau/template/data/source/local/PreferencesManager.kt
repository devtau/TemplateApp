package com.devtau.template.data.source.local

import android.content.Context
import android.content.SharedPreferences
import com.devtau.template.BuildConfig
import javax.inject.Inject

/**
 * Manager working with list of [SharedPreferences]
 */
interface PreferencesManager {

    /**
     * Timestamp of last successful synchronization with backend in milliseconds
     */
    var lastSyncDate: Long
}

class PreferencesManagerImpl @Inject constructor(context: Context): PreferencesManager {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override var lastSyncDate: Long
        get() = prefs.getLong(LAST_SYNC_DATE, 0)
        set(value) = with(prefs.edit()) {
            putLong(LAST_SYNC_DATE, value)
            apply()
        }

    fun clear() = prefs.edit()?.clear()?.apply()

    companion object {
        private const val PREFERENCES_NAME = BuildConfig.DATABASE_NAME + "_prefs"
        private const val LAST_SYNC_DATE = "lastSyncDate"
    }
}