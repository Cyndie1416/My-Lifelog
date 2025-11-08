package com.lifelog.app.domain.model

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDate

@Serializable
data class JournalEntry(
    val id: Long = 0L,
    val title: String,
    val content: String,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
    val noteDate: LocalDate = LocalDate.now(),
    val fontFamily: String = "Nunito",
    val fontSizeSp: Float = 16f,
    val textColorHex: String = "#1C1B1F",
    val stickerIds: List<String> = emptyList(),
    val attachments: List<Attachment> = emptyList(),
    val isPinned: Boolean = false,
    val mood: String? = null,
    val tags: List<String> = emptyList(),
    val goalId: Long? = null
)

