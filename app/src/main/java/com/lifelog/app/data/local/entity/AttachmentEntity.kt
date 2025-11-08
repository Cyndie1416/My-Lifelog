package com.lifelog.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.lifelog.app.domain.model.AttachmentType
import java.time.Instant

@Entity(
    tableName = "attachments",
    foreignKeys = [
        ForeignKey(
            entity = JournalEntryEntity::class,
            parentColumns = ["id"],
            childColumns = ["entry_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("entry_id"),
        Index("uri", unique = false)
    ]
)
data class AttachmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "entry_id") val entryId: Long,
    val uri: String,
    val type: AttachmentType,
    val description: String?,
    @ColumnInfo(name = "width") val width: Float?,
    @ColumnInfo(name = "height") val height: Float?,
    @ColumnInfo(name = "created_at") val createdAt: Instant
)

