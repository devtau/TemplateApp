package com.devtau.template.data.source

import android.content.SharedPreferences
import com.devtau.template.data.source.local.PreferencesManager

/**
 * Mocks manager working with list of [SharedPreferences]
 */
object FakePreferencesManager: PreferencesManager {

    override var lastSyncDate: Long = 0
}