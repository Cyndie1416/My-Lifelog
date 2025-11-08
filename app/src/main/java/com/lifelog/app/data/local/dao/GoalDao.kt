package com.lifelog.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lifelog.app.data.local.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Query("SELECT * FROM goals ORDER BY target_date ASC")
    fun observeGoals(): Flow<List<GoalEntity>>

    @Upsert
    suspend fun upsert(goal: GoalEntity): Long

    @Query("UPDATE goals SET status = :status WHERE id = :goalId")
    suspend fun updateStatus(goalId: Long, status: com.lifelog.app.domain.model.GoalStatus)

    @Query("UPDATE goals SET completed_at = :completedAt WHERE id = :goalId")
    suspend fun updateCompletion(goalId: Long, completedAt: java.time.Instant?)

    @Query("DELETE FROM goals WHERE id = :goalId")
    suspend fun delete(goalId: Long)
}

