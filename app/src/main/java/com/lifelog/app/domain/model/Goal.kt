package com.lifelog.app.domain.model

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDate

@Serializable
data class Goal(
    val id: Long = 0L,
    val title: String,
    val description: String? = null,
    val targetDate: LocalDate,
    val status: GoalStatus = GoalStatus.IN_PROGRESS,
    val createdAt: Instant = Instant.now(),
    val completedAt: Instant? = null,
    val relatedEntryIds: List<Long> = emptyList()
)

@Serializable
enum class GoalStatus {
    IN_PROGRESS,
    COMPLETED,
    SKIPPED
}

