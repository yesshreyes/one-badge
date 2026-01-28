package com.one_badge.app.data.local.cachedTeam

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedTeamDao {
    @Query("SELECT * FROM cached_teams ORDER BY teamName ASC")
    suspend fun getAllCachedTeams(): List<CachedTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: CachedTeam)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teams: List<CachedTeam>)

    @Query("SELECT * FROM cached_teams WHERE teamName = :teamName LIMIT 1")
    suspend fun getTeamByName(teamName: String): CachedTeam?

    @Query("DELETE FROM cached_teams")
    suspend fun clearAllTeams()

    @Query("DELETE FROM cached_teams WHERE cachedAt < :timestamp")
    suspend fun deleteOldCache(timestamp: Long)

    @Query("SELECT COUNT(*) FROM cached_teams")
    suspend fun getCachedTeamsCount(): Int
}
