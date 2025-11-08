package com.lifelog.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lifelog.app.data.local.converter.RoomTypeConverters
import com.lifelog.app.data.local.dao.AttachmentDao
import com.lifelog.app.data.local.dao.GoalDao
import com.lifelog.app.data.local.dao.JournalEntryDao
import com.lifelog.app.data.local.entity.AttachmentEntity
import com.lifelog.app.data.local.entity.GoalEntity
import com.lifelog.app.data.local.entity.JournalEntryEntity

@Database(
    entities = [
        JournalEntryEntity::class,
        AttachmentEntity::class,
        GoalEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalEntryDao(): JournalEntryDao
    abstract fun goalDao(): GoalDao
    abstract fun attachmentDao(): AttachmentDao
}

