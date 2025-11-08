package com.lifelog.app.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.lifelog.app.data.local.entity.AttachmentEntity
import com.lifelog.app.data.local.entity.JournalEntryEntity

data class JournalEntryWithAttachments(
    @Embedded val entry: JournalEntryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "entry_id"
    )
    val attachments: List<AttachmentEntity>
)

