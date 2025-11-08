package com.lifelog.app.di

import com.lifelog.app.data.repository.GoalRepository
import com.lifelog.app.data.repository.OfflineFirstGoalRepository
import com.lifelog.app.data.repository.OfflineFirstJournalRepository
import com.lifelog.app.data.repository.JournalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindJournalRepository(
        impl: OfflineFirstJournalRepository
    ): JournalRepository

    @Binds
    @Singleton
    abstract fun bindGoalRepository(
        impl: OfflineFirstGoalRepository
    ): GoalRepository
}

