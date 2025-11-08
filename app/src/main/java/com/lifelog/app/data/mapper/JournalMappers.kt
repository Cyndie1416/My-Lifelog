package com.lifelog.app.data.mapper

import com.lifelog.app.data.local.entity.AttachmentEntity
import com.lifelog.app.data.local.entity.GoalEntity
import com.lifelog.app.data.local.entity.JournalEntryEntity
import com.lifelog.app.data.local.model.JournalEntryWithAttachments
import com.lifelog.app.domain.model.Attachment
import com.lifelog.app.domain.model.Goal
import com.lifelog.app.domain.model.JournalEntry

fun JournalEntryWithAttachments.toDomain(): JournalEntry =
    entry.toDomain(attachments)

fun JournalEntryEntity.toDomain(attachments: List<AttachmentEntity>): JournalEntry =
    JournalEntry(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        noteDate = noteDate,
        fontFamily = fontFamily,
        fontSizeSp = fontSizeSp,
        textColorHex = textColorHex,
        stickerIds = stickerIds,
        attachments = attachments.map { it.toDomain() },
        isPinned = isPinned,
        mood = mood,
        tags = tags,
        goalId = goalId
    )

fun AttachmentEntity.toDomain(): Attachment =
    Attachment(
        id = id,
        entryId = entryId,
        uri = uri,
        type = type,
        description = description,
        width = width,
        height = height,
        createdAt = createdAt
    )

fun GoalEntity.toDomain(): Goal =
    Goal(
        id = id,
        title = title,
        description = description,
        targetDate = targetDate,
        status = status,
        createdAt = createdAt,
        completedAt = completedAt,
        relatedEntryIds = relatedEntryIds
    )

fun JournalEntry.toEntity(): JournalEntryEntity =
    JournalEntryEntity(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        noteDate = noteDate,
        fontFamily = fontFamily,
        fontSizeSp = fontSizeSp,
        textColorHex = textColorHex,
        stickerIds = stickerIds,
        mood = mood,
        isPinned = isPinned,
        tags = tags,
        goalId = goalId
    )

fun Attachment.toEntity(): AttachmentEntity =
    AttachmentEntity(
        id = id,
        entryId = entryId,
        uri = uri,
        type = type,
        description = description,
        width = width,
        height = height,
        createdAt = createdAt
    )

fun Goal.toEntity(): GoalEntity =
    GoalEntity(
        id = id,
        title = title,
        description = description,
        targetDate = targetDate,
        status = status,
        createdAt = createdAt,
        completedAt = completedAt,
        relatedEntryIds = relatedEntryIds
    )

