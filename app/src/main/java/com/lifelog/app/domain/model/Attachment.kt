package com.lifelog.app.domain.model

import androidx.compose.ui.geometry.Size
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Attachment(
    val id: Long = 0,
    val entryId: Long = 0,
    val uri: String,
    val type: AttachmentType,
    val description: String? = null,
    val width: Float? = null,
    val height: Float? = null,
    val createdAt: Instant = Instant.now()
) {
    val aspectRatio: Float?
        get() = if (width != null && height != null && height != 0f) {
            width / height
        } else {
            null
        }
}

@Serializable
enum class AttachmentType {
    IMAGE,
    AUDIO,
    VIDEO,
    STICKER
}

