package com.lifelog.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.lifelog.app.ui.MyLifelogApp
import com.lifelog.app.ui.navigation.rememberMyLifelogAppState
import com.lifelog.app.ui.theme.AppearanceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appearanceViewModel: AppearanceViewModel = hiltViewModel()
            val appearanceState by appearanceViewModel.appearance.collectAsStateWithLifecycle()
            val appState = rememberMyLifelogAppState()
            val context = LocalContext.current

            MyLifelogApp(
                appState = appState,
                appearance = appearanceState,
                onCreateJournalEntry = {
                    Toast.makeText(
                        context,
                        "Rich note composer coming soon.",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onCreateGoal = {
                    Toast.makeText(
                        context,
                        "Goal planner coming soon.",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onRequestJournalDetail = { entryId ->
                    Toast.makeText(
                        context,
                        "Opening entry #$entryId (editor upcoming).",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onRequestSync = {
                    Toast.makeText(
                        context,
                        "Sync queued. Connect to Wi-Fi to upload.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}

