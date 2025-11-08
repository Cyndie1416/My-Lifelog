package com.lifelog.app.sync

import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface CloudSyncManager {
    val isSyncing: Flow<Boolean>
    val lastSyncedAt: Flow<Instant?>

    suspend fun queueJournalEntrySync(entryId: Long)
    suspend fun queueJournalEntryDeletion(entryId: Long)
    suspend fun queueGoalSync(goalId: Long)
    suspend fun queueGoalDeletion(goalId: Long)

    suspend fun triggerFullSync()
}

