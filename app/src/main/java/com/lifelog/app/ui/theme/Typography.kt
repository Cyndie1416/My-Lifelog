package com.lifelog.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.GoogleFontProvider

private val provider = GoogleFontProvider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = com.lifelog.app.R.array.com_google_android_gms_fonts_certs
)

private val displayFontName = GoogleFont("Poppins")
private val bodyFontName = GoogleFont("Nunito")

private val DisplayFontFamily = FontFamily(
    Font(googleFont = displayFontName, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = displayFontName, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = displayFontName, fontProvider = provider, weight = FontWeight.Medium)
)

private val BodyFontFamily = FontFamily(
    Font(googleFont = bodyFontName, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = bodyFontName, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = bodyFontName, fontProvider = provider, weight = FontWeight.Bold)
)

val AppTypography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = DisplayFontFamily),
        displayMedium = displayMedium.copy(fontFamily = DisplayFontFamily),
        displaySmall = displaySmall.copy(fontFamily = DisplayFontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = DisplayFontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = DisplayFontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = DisplayFontFamily),
        titleLarge = titleLarge.copy(fontFamily = DisplayFontFamily),
        titleMedium = titleMedium.copy(fontFamily = DisplayFontFamily),
        titleSmall = titleSmall.copy(fontFamily = DisplayFontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = BodyFontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = BodyFontFamily),
        bodySmall = bodySmall.copy(fontFamily = BodyFontFamily),
        labelLarge = labelLarge.copy(fontFamily = BodyFontFamily),
        labelMedium = labelMedium.copy(fontFamily = BodyFontFamily),
        labelSmall = labelSmall.copy(fontFamily = BodyFontFamily)
    )
}

