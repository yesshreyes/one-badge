package com.example.one_badge.di

import android.content.Context
import com.example.one_badge.data.local.AppDatabase
import com.example.one_badge.data.local.cachedTeam.CachedTeamDao
import com.example.one_badge.data.local.userPreferences.UserPreferencesDao
import com.example.one_badge.data.remote.RetrofitInstance
import com.example.one_badge.data.repository.TeamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    fun provideUserPreferencesDao(db: AppDatabase) =
        db.userPreferencesDao()

    @Provides
    fun provideCachedTeamDao(db: AppDatabase) =
        db.cachedTeamDao()

    @Provides
    @Singleton
    fun provideTeamRepository(
        userPreferencesDao: UserPreferencesDao,
        cachedTeamDao: CachedTeamDao,
    ): TeamRepository =
        TeamRepository(
            RetrofitInstance.api,
            userPreferencesDao,
            cachedTeamDao,
        )
}
