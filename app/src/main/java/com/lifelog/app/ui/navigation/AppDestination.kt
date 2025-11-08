package com.lifelog.app.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class AppDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
)

object Destinations {
    const val Journal = "journal"
    const val Goals = "goals"
    const val Settings = "settings"
}

