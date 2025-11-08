package com.lifelog.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifelog.app.data.repository.JournalRepository
import com.lifelog.app.domain.model.JournalEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class JournalUiState(
    val entries: List<JournalEntry> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val repository: JournalRepository
) : ViewModel() {

    private val _state = MutableStateFlow(JournalUiState(isLoading = true))
    val state: StateFlow<JournalUiState> = _state.asStateFlow()

    init {
        observeEntries()
    }

    private fun observeEntries() {
        viewModelScope.launch {
            repository.observeEntries()
                .collect { entries ->
                    _state.update {
                        it.copy(
                            entries = entries,
                            isLoading = false,
                            isEmpty = entries.isEmpty(),
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun saveEntry(entry: JournalEntry) {
        viewModelScope.launch {
            runCatching {
                repository.upsert(
                    entry.copy(
                        updatedAt = Instant.now()
                    )
                )
            }.onFailure { throwable ->
                _state.update { it.copy(errorMessage = throwable.message) }
            }
        }
    }

    fun deleteEntry(entryId: Long) {
        viewModelScope.launch {
            runCatching {
                repository.delete(entryId)
            }.onFailure { throwable ->
                _state.update { it.copy(errorMessage = throwable.message) }
            }
        }
    }
}

