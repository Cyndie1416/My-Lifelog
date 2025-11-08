package com.lifelog.app.settings.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.appSettingsDataStore by preferencesDataStore(
    name = "app_settings"
)

