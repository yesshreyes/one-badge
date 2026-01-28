package com.one_badge.app.data.local.userPreferences

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferencesDao {
    @Query("SELECT * FROM user_preferences WHERE id = 1 LIMIT 1")
    fun getUserPreferences(): Flow<UserPreferences?>

    @Query("SELECT * FROM user_preferences WHERE id = 1 LIMIT 1")
    suspend fun getUserPreferencesOnce(): UserPreferences?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun saveUserPreferences(preferences: UserPreferences)

    @Query("UPDATE user_preferences SET selectedTeam = :teamName, lastSelectedAt = :timestamp WHERE id = 1")
    suspend fun updateSelectedTeam(
        teamName: String,
        timestamp: Long = System.currentTimeMillis(),
    )

    @Query("UPDATE user_preferences SET isFirstLaunch = 0 WHERE id = 1")
    suspend fun markFirstLaunchComplete()

    @Query("DELETE FROM user_preferences")
    suspend fun clearPreferences()
}
