package com.example.one_badge.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.one_badge.data.local.cachedTeam.CachedTeam
import com.example.one_badge.data.local.cachedTeam.CachedTeamDao
import com.example.one_badge.data.local.userPreferences.UserPreferences
import com.example.one_badge.data.local.userPreferences.UserPreferencesDao

@Database(
    entities = [UserPreferences::class, CachedTeam::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userPreferencesDao(): UserPreferencesDao

    abstract fun cachedTeamDao(): CachedTeamDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "onebadge_database",
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                Companion.instance = instance
                instance
            }
        }
    }
}
