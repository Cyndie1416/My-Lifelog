package com.lifelog.app.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lifelog.app.settings.model.AppAppearancePreferences
import com.lifelog.app.settings.model.AppThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val USE_DYNAMIC_COLOR = booleanPreferencesKey("use_dynamic_color")
    }

    val appearancePreferences: Flow<AppAppearancePreferences> =
        dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { preferences ->
                val mode = preferences[Keys.THEME_MODE]?.let { AppThemeMode.valueOf(it) }
                    ?: AppThemeMode.SYSTEM
                val dynamic = preferences[Keys.USE_DYNAMIC_COLOR] ?: true
                AppAppearancePreferences(mode, dynamic)
            }

    suspend fun setThemeMode(mode: AppThemeMode) {
        dataStore.edit { preferences ->
            preferences[Keys.THEME_MODE] = mode.name
        }
    }

    suspend fun setUseDynamicColor(useDynamic: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.USE_DYNAMIC_COLOR] = useDynamic
        }
    }
}

