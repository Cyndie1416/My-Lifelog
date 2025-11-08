package com.lifelog.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lifelog.app.ui.goals.GoalsScreen
import com.lifelog.app.ui.goals.GoalsViewModel
import com.lifelog.app.ui.home.JournalScreen
import com.lifelog.app.ui.home.JournalViewModel
import com.lifelog.app.ui.settings.SettingsScreen
import com.lifelog.app.ui.settings.SettingsViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    onCreateJournalEntry: () -> Unit,
    onCreateGoal: () -> Unit,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    onRequestJournalDetail: (Long) -> Unit,
    onRequestSync: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Journal,
        modifier = modifier
    ) {
        composable(Destinations.Journal) {
            val viewModel: JournalViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            JournalScreen(
                state = state,
                onAddEntry = onCreateJournalEntry,
                onEntryClick = onRequestJournalDetail
            )
        }

        composable(Destinations.Goals) {
            val viewModel: GoalsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            GoalsScreen(
                state = state,
                onAddGoal = onCreateGoal,
                onStatusChange = { goal, completed ->
                    val status = if (completed) {
                        com.lifelog.app.domain.model.GoalStatus.COMPLETED
                    } else {
                        com.lifelog.app.domain.model.GoalStatus.IN_PROGRESS
                    }
                    viewModel.updateStatus(goal.id, status)
                }
            )
        }

        composable(Destinations.Settings) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            SettingsScreen(
                state = state,
                onThemeSelected = {
                    viewModel.setThemeMode(it)
                },
                onDynamicColorToggle = {
                    viewModel.setUseDynamicColor(it)
                },
                onSyncClick = {
                    viewModel.triggerSync()
                    onRequestSync()
                }
            )
        }
    }
}

