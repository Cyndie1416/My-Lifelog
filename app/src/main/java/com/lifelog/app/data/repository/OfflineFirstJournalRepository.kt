package com.lifelog.app.data.repository

import com.lifelog.app.data.local.dao.AttachmentDao
import com.lifelog.app.data.local.dao.JournalEntryDao
import com.lifelog.app.data.mapper.toDomain
import com.lifelog.app.data.mapper.toEntity
import com.lifelog.app.domain.model.JournalEntry
import com.lifelog.app.di.IoDispatcher
import com.lifelog.app.sync.CloudSyncManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstJournalRepository @Inject constructor(
    private val journalEntryDao: JournalEntryDao,
    private val attachmentDao: AttachmentDao,
    private val cloudSyncManager: CloudSyncManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : JournalRepository {

    override fun observeEntries(): Flow<List<JournalEntry>> =
        journalEntryDao.observeEntries().map { entries ->
            entries.map { it.toDomain() }
        }

    override fun observeEntry(id: Long): Flow<JournalEntry?> =
        journalEntryDao.observeEntry(id).map { it?.toDomain() }

    override suspend fun upsert(entry: JournalEntry) = withContext(ioDispatcher) {
        val entryId = journalEntryDao.upsert(entry.toEntity()).let { generatedId ->
            if (entry.id == 0L) generatedId else entry.id
        }

        val updatedAttachments = entry.attachments.map { attachment ->
            attachment.copy(entryId = entryId).toEntity()
        }
        attachmentDao.deleteByEntryId(entryId)
        if (updatedAttachments.isNotEmpty()) {
            attachmentDao.upsert(updatedAttachments)
        }

        cloudSyncManager.queueJournalEntrySync(entryId)
    }

    override suspend fun delete(id: Long) = withContext(ioDispatcher) {
        attachmentDao.deleteByEntryId(id)
        journalEntryDao.deleteById(id)
        cloudSyncManager.queueJournalEntryDeletion(id)
    }
}

