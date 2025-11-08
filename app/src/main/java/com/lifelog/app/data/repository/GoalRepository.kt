package com.lifelog.app.data.repository

import com.lifelog.app.domain.model.Goal
import com.lifelog.app.domain.model.GoalStatus
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface GoalRepository {
    fun observeGoals(): Flow<List<Goal>>
    suspend fun upsert(goal: Goal): Long
    suspend fun updateStatus(goalId: Long, status: GoalStatus, completedAt: Instant? = null)
    suspend fun delete(goalId: Long)
}

