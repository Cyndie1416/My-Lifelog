package com.lifelog.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberMyLifelogAppState(
    navController: NavHostController = rememberNavController()
): MyLifelogAppState {
    return remember(navController) {
        MyLifelogAppState(navController)
    }
}

@Stable
class MyLifelogAppState(
    val navController: NavHostController
) {
    private val bottomDestinations = listOf(
        Destinations.Journal,
        Destinations.Goals,
        Destinations.Settings
    )

    val currentDestinationRoute: String?
        @Composable get() =
            navController.currentBackStackEntryAsState().value?.destination?.route

    fun navigateTo(route: String) {
        if (route == navController.currentDestination?.route) return
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun isBottomDestination(route: String?): Boolean =
        route != null && bottomDestinations.contains(route)
}

