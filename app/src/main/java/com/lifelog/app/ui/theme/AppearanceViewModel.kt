package com.lifelog.app.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifelog.app.settings.AppSettingsRepository
import com.lifelog.app.settings.model.AppAppearancePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class AppearanceViewModel @Inject constructor(
    settingsRepository: AppSettingsRepository
) : ViewModel() {

    val appearance: StateFlow<AppAppearancePreferences> =
        settingsRepository.appearancePreferences
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AppAppearancePreferences()
            )
}

