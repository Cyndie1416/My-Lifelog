package com.lifelog.app.data.repository

import com.lifelog.app.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun observeEntries(): Flow<List<JournalEntry>>
    fun observeEntry(id: Long): Flow<JournalEntry?>
    suspend fun upsert(entry: JournalEntry)
    suspend fun delete(id: Long)
}

