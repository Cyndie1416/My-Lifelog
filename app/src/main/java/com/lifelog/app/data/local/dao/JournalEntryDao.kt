package com.lifelog.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.lifelog.app.data.local.entity.JournalEntryEntity
import com.lifelog.app.data.local.model.JournalEntryWithAttachments
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalEntryDao {

    @Transaction
    @Query(
        """
            SELECT * FROM journal_entries
            ORDER BY note_date DESC, updated_at DESC
        """
    )
    fun observeEntries(): Flow<List<JournalEntryWithAttachments>>

    @Transaction
    @Query("SELECT * FROM journal_entries WHERE id = :id")
    fun observeEntry(id: Long): Flow<JournalEntryWithAttachments?>

    @Upsert
    suspend fun upsert(entry: JournalEntryEntity): Long

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteById(id: Long)
}

