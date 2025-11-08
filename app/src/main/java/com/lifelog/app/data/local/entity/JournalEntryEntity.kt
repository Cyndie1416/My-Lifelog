package com.lifelog.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val content: String,
    @ColumnInfo(name = "created_at") val createdAt: Instant,
    @ColumnInfo(name = "updated_at") val updatedAt: Instant,
    @ColumnInfo(name = "note_date") val noteDate: LocalDate,
    @ColumnInfo(name = "font_family") val fontFamily: String,
    @ColumnInfo(name = "font_size_sp") val fontSizeSp: Float,
    @ColumnInfo(name = "text_color_hex") val textColorHex: String,
    @ColumnInfo(name = "sticker_ids") val stickerIds: List<String>,
    val mood: String?,
    @ColumnInfo(name = "is_pinned") val isPinned: Boolean,
    @ColumnInfo(name = "tags") val tags: List<String>,
    @ColumnInfo(name = "goal_id") val goalId: Long?
)

