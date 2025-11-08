package com.lifelog.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.lifelog.app.settings.model.AppAppearancePreferences
import com.lifelog.app.ui.navigation.AppDestination
import com.lifelog.app.ui.navigation.AppNavHost
import com.lifelog.app.ui.navigation.Destinations
import com.lifelog.app.ui.navigation.MyLifelogAppState
import com.lifelog.app.ui.theme.MyLifelogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLifelogApp(
    appState: MyLifelogAppState,
    appearance: AppAppearancePreferences,
    onCreateJournalEntry: () -> Unit,
    onCreateGoal: () -> Unit,
    onRequestJournalDetail: (Long) -> Unit,
    onRequestSync: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val bottomDestinations = remember {
        listOf(
            AppDestination(
                route = Destinations.Journal,
                label = "Journal",
                icon = Icons.Rounded.MenuBook
            ),
            AppDestination(
                route = Destinations.Goals,
                label = "Goals",
                icon = Icons.Rounded.TaskAlt
            ),
            AppDestination(
                route = Destinations.Settings,
                label = "Settings",
                icon = Icons.Rounded.Settings
            )
        )
    }

    MyLifelogTheme(
        themeMode = appearance.themeMode,
        useDynamicColor = appearance.useDynamicColor
    ) {
        Scaffold(
            modifier = modifier,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                if (appState.isBottomDestination(appState.currentDestinationRoute)) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        bottomDestinations.forEach { destination ->
                            val selected = appState.currentDestinationRoute == destination.route
                            NavigationBarItem(
                                selected = selected,
                                onClick = { appState.navigateTo(destination.route) },
                                icon = {
                                    androidx.compose.material3.Icon(
                                        imageVector = destination.icon,
                                        contentDescription = destination.label
                                    )
                                },
                                label = {
                                    androidx.compose.material3.Text(destination.label)
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = appState.navController,
                onCreateJournalEntry = onCreateJournalEntry,
                onCreateGoal = onCreateGoal,
                onRequestJournalDetail = onRequestJournalDetail,
                onRequestSync = onRequestSync,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

