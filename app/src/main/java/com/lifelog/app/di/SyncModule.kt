package com.lifelog.app.di

import com.lifelog.app.sync.CloudSyncManager
import com.lifelog.app.sync.NoopCloudSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {

    @Binds
    @Singleton
    abstract fun bindCloudSyncManager(
        impl: NoopCloudSyncManager
    ): CloudSyncManager
}

