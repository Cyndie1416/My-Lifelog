package com.lifelog.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lifelog.app.settings.model.AppThemeMode

private val LightColors = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    error = ErrorLight,
    onError = OnErrorLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight
)

private val DarkColors = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    error = ErrorDark,
    onError = OnErrorDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark
)

@Composable
fun MyLifelogTheme(
    themeMode: AppThemeMode,
    useDynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isSystemDark = isSystemInDarkTheme()
    val useDarkTheme = when (themeMode) {
        AppThemeMode.SYSTEM -> isSystemDark
        AppThemeMode.DARK -> true
        AppThemeMode.LIGHT -> false
    }

    val colorScheme = rememberColorScheme(
        contextIsDynamicEligible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
        useDynamicColor = useDynamicColor,
        useDarkTheme = useDarkTheme
    )

    val systemUi = rememberSystemUiController()
    SideEffect {
        systemUi.setSystemBarsColor(
            color = colorScheme.background,
            darkIcons = !useDarkTheme
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as? Activity)?.window?.apply {
                statusBarColor = colorScheme.background.toArgb()
                navigationBarColor = colorScheme.surface.toArgb()
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

@Composable
private fun rememberColorScheme(
    contextIsDynamicEligible: Boolean,
    useDynamicColor: Boolean,
    useDarkTheme: Boolean
): ColorScheme {
    val context = LocalContext.current
    return when {
        contextIsDynamicEligible && useDynamicColor && useDarkTheme -> dynamicDarkColorScheme(context)
        contextIsDynamicEligible && useDynamicColor && !useDarkTheme -> dynamicLightColorScheme(context)
        useDarkTheme -> DarkColors
        else -> LightColors
    }
}

