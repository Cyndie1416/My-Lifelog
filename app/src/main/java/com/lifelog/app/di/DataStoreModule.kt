package com.lifelog.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lifelog.app.settings.AppSettingsRepository
import com.lifelog.app.settings.datastore.appSettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.appSettingsDataStore

    @Provides
    @Singleton
    fun provideAppSettingsRepository(
        dataStore: DataStore<Preferences>
    ): AppSettingsRepository = AppSettingsRepository(dataStore)
}

