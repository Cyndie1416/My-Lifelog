package com.lifelog.app.sync

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoopCloudSyncManager @Inject constructor() : CloudSyncManager {

    private val syncing = MutableStateFlow(false)
    private val lastSynced = MutableStateFlow<Instant?>(null)

    override val isSyncing: Flow<Boolean> = syncing
    override val lastSyncedAt: Flow<Instant?> = lastSynced

    override suspend fun queueJournalEntrySync(entryId: Long) {
        markSynced()
    }

    override suspend fun queueJournalEntryDeletion(entryId: Long) {
        markSynced()
    }

    override suspend fun queueGoalSync(goalId: Long) {
        markSynced()
    }

    override suspend fun queueGoalDeletion(goalId: Long) {
        markSynced()
    }

    override suspend fun triggerFullSync() {
        markSynced()
    }

    private fun markSynced() {
        syncing.update { true }
        lastSynced.update { Instant.now() }
        syncing.update { false }
    }
}

