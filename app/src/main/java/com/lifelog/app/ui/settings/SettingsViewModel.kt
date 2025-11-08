package com.lifelog.app.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifelog.app.settings.AppSettingsRepository
import com.lifelog.app.settings.model.AppAppearancePreferences
import com.lifelog.app.settings.model.AppThemeMode
import com.lifelog.app.sync.CloudSyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val appearance: AppAppearancePreferences = AppAppearancePreferences(),
    val isSyncing: Boolean = false,
    val lastSyncedAt: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: AppSettingsRepository,
    private val cloudSyncManager: CloudSyncManager
) : ViewModel() {

    private val formatter = java.time.format.DateTimeFormatter.ofPattern("MMM d, yyyy • h:mm a")
        .withLocale(java.util.Locale.getDefault())
        .withZone(java.time.ZoneId.systemDefault())

    val state: StateFlow<SettingsUiState> =
        combine(
            settingsRepository.appearancePreferences,
            cloudSyncManager.isSyncing,
            cloudSyncManager.lastSyncedAt
        ) { appearance, isSyncing, lastSynced ->
            SettingsUiState(
                appearance = appearance,
                isSyncing = isSyncing,
                lastSyncedAt = lastSynced?.let { formatter.format(it) }
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState()
            )

    fun setThemeMode(mode: AppThemeMode) {
        viewModelScope.launch {
            settingsRepository.setThemeMode(mode)
        }
    }

    fun setUseDynamicColor(useDynamic: Boolean) {
        viewModelScope.launch {
            settingsRepository.setUseDynamicColor(useDynamic)
        }
    }

    fun triggerSync() {
        viewModelScope.launch {
            cloudSyncManager.triggerFullSync()
        }
    }
}

