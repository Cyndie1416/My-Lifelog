package com.lifelog.app.ui.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifelog.app.data.repository.GoalRepository
import com.lifelog.app.domain.model.Goal
import com.lifelog.app.domain.model.GoalStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GoalsUiState(
    val goals: List<Goal> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val repository: GoalRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GoalsUiState())
    val state: StateFlow<GoalsUiState> = _state.asStateFlow()

    init {
        observeGoals()
    }

    private fun observeGoals() {
        viewModelScope.launch {
            repository.observeGoals()
                .collect { goals ->
                    _state.update {
                        it.copy(
                            goals = goals,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun upsertGoal(goal: Goal) {
        viewModelScope.launch {
            runCatching { repository.upsert(goal) }
                .onFailure { throwable ->
                    _state.update { it.copy(errorMessage = throwable.message) }
                }
        }
    }

    fun updateStatus(goalId: Long, status: GoalStatus) {
        viewModelScope.launch {
            val completion = if (status == GoalStatus.COMPLETED) Instant.now() else null
            runCatching { repository.updateStatus(goalId, status, completion) }
                .onFailure { throwable ->
                    _state.update { it.copy(errorMessage = throwable.message) }
                }
        }
    }

    fun deleteGoal(goalId: Long) {
        viewModelScope.launch {
            runCatching { repository.delete(goalId) }
                .onFailure { throwable ->
                    _state.update { it.copy(errorMessage = throwable.message) }
                }
        }
    }
}

