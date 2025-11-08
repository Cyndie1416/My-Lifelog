package com.lifelog.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lifelog.app.data.local.entity.AttachmentEntity

@Dao
interface AttachmentDao {

    @Upsert
    suspend fun upsert(attachments: List<AttachmentEntity>)

    @Query("DELETE FROM attachments WHERE entry_id = :entryId")
    suspend fun deleteByEntryId(entryId: Long)

    @Query("DELETE FROM attachments WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Long>)
}

