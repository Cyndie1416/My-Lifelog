package com.lifelog.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lifelog.app.domain.model.GoalStatus
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val description: String?,
    @ColumnInfo(name = "target_date") val targetDate: LocalDate,
    val status: GoalStatus,
    @ColumnInfo(name = "created_at") val createdAt: Instant,
    @ColumnInfo(name = "completed_at") val completedAt: Instant?,
    @ColumnInfo(name = "related_entry_ids") val relatedEntryIds: List<Long>
)

