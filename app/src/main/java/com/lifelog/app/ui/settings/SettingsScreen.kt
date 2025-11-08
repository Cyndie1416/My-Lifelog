package com.lifelog.app.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brightness4
import androidx.compose.material.icons.rounded.CloudSync
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifelog.app.settings.model.AppThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onThemeSelected: (AppThemeMode) -> Unit,
    onDynamicColorToggle: (Boolean) -> Unit,
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Customize your vibe and manage sync",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            AppearanceCard(
                state = state,
                onThemeSelected = onThemeSelected,
                onDynamicColorToggle = onDynamicColorToggle
            )
            SyncCard(
                state = state,
                onSyncClick = onSyncClick
            )
        }
    }
}

@Composable
private fun AppearanceCard(
    state: SettingsUiState,
    onThemeSelected: (AppThemeMode) -> Unit,
    onDynamicColorToggle: (Boolean) -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Brightness4,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThemeOption(
                    label = "Match system",
                    description = "Automatically follow your device appearance.",
                    selected = state.appearance.themeMode == AppThemeMode.SYSTEM,
                    onSelected = { onThemeSelected(AppThemeMode.SYSTEM) }
                )
                ThemeOption(
                    label = "Light",
                    description = "Bright, airy vibe ideal for daytime journaling.",
                    selected = state.appearance.themeMode == AppThemeMode.LIGHT,
                    onSelected = { onThemeSelected(AppThemeMode.LIGHT) }
                )
                ThemeOption(
                    label = "Dark",
                    description = "Relaxed look that's easy on your eyes at night.",
                    selected = state.appearance.themeMode == AppThemeMode.DARK,
                    onSelected = { onThemeSelected(AppThemeMode.DARK) }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Dynamic colors",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Blend with your wallpaper when supported.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Switch(
                    checked = state.appearance.useDynamicColor,
                    onCheckedChange = onDynamicColorToggle
                )
            }
        }
    }
}

@Composable
private fun ThemeOption(
    label: String,
    description: String,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelected
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun SyncCard(
    state: SettingsUiState,
    onSyncClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.CloudSync,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        text = "Cloud sync",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Keep notes offline-first and sync when you choose.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val statusText = when {
                    state.isSyncing -> "Sync in progress…"
                    state.lastSyncedAt != null -> "Last synced ${state.lastSyncedAt}"
                    else -> "Not synced yet"
                }
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (state.isSyncing) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            Button(
                onClick = onSyncClick,
                enabled = !state.isSyncing,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (state.isSyncing) "Syncing…" else "Sync now",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

