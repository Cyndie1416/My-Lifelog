package com.lifelog.app.di

import android.content.Context
import androidx.room.Room
import com.lifelog.app.data.local.AppDatabase
import com.lifelog.app.data.local.converter.RoomTypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        json: Json
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_lifelog.db"
        )
            .addTypeConverter(RoomTypeConverters(json))
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()

    @Provides
    fun provideJournalEntryDao(database: AppDatabase) = database.journalEntryDao()

    @Provides
    fun provideAttachmentDao(database: AppDatabase) = database.attachmentDao()

    @Provides
    fun provideGoalDao(database: AppDatabase) = database.goalDao()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
}

