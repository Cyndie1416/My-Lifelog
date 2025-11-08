package com.lifelog.app.settings.model

data class AppAppearancePreferences(
    val themeMode: AppThemeMode = AppThemeMode.SYSTEM,
    val useDynamicColor: Boolean = true
)

