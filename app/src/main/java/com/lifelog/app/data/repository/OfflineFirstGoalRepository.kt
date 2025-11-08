package com.lifelog.app.data.repository

import com.lifelog.app.data.local.dao.GoalDao
import com.lifelog.app.data.mapper.toDomain
import com.lifelog.app.data.mapper.toEntity
import com.lifelog.app.di.IoDispatcher
import com.lifelog.app.domain.model.Goal
import com.lifelog.app.domain.model.GoalStatus
import com.lifelog.app.sync.CloudSyncManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstGoalRepository @Inject constructor(
    private val goalDao: GoalDao,
    private val cloudSyncManager: CloudSyncManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GoalRepository {

    override fun observeGoals(): Flow<List<Goal>> =
        goalDao.observeGoals().map { goals -> goals.map { it.toDomain() } }

    override suspend fun upsert(goal: Goal): Long = withContext(ioDispatcher) {
        val id = goalDao.upsert(goal.toEntity())
        cloudSyncManager.queueGoalSync(if (goal.id == 0L) id else goal.id)
        id
    }

    override suspend fun updateStatus(
        goalId: Long,
        status: GoalStatus,
        completedAt: Instant?
    ) = withContext(ioDispatcher) {
        goalDao.updateStatus(goalId, status)
        goalDao.updateCompletion(goalId, completedAt)
        cloudSyncManager.queueGoalSync(goalId)
    }

    override suspend fun delete(goalId: Long) = withContext(ioDispatcher) {
        goalDao.delete(goalId)
        cloudSyncManager.queueGoalDeletion(goalId)
    }
}

