package com.one_badge.app.di

import android.content.Context
import com.one_badge.app.data.local.AppDatabase
import com.one_badge.app.data.local.cachedTeam.CachedTeamDao
import com.one_badge.app.data.local.userPreferences.UserPreferencesDao
import com.one_badge.app.data.remote.RetrofitInstance
import com.one_badge.app.data.repository.TeamRepository
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
