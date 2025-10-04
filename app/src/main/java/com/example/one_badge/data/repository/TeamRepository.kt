package com.example.one_badge.data.repository

import com.example.one_badge.data.local.cachedTeam.CachedTeam
import com.example.one_badge.data.local.cachedTeam.CachedTeamDao
import com.example.one_badge.data.local.userPreferences.UserPreferences
import com.example.one_badge.data.local.userPreferences.UserPreferencesDao
import com.example.one_badge.data.models.TeamResponse
import com.example.one_badge.data.remote.SportsDbApi
import kotlinx.coroutines.flow.Flow

class TeamRepository(
    private val api: SportsDbApi,
    private val userPreferencesDao: UserPreferencesDao,
    private val cachedTeamDao: CachedTeamDao,
) {
    companion object {
        private const val CACHE_DURATION = 24 * 60 * 60 * 1000L
    }

    suspend fun getTeamByName(teamName: String): TeamResponse {
        return api.searchTeams(teamName)
    }

    suspend fun getCachedTeams(): List<CachedTeam> {
        return cachedTeamDao.getAllCachedTeams()
    }

    suspend fun cacheTeams(teams: List<Pair<String, String>>) {
        val cachedTeams =
            teams.map { (name, badge) ->
                CachedTeam(
                    teamName = name,
                    badgeUrl = badge,
                    cachedAt = System.currentTimeMillis(),
                )
            }
        cachedTeamDao.insertTeams(cachedTeams)
    }

    suspend fun isCacheValid(): Boolean {
        val count = cachedTeamDao.getCachedTeamsCount()
        if (count == 0) return false

        val oldestAllowed = System.currentTimeMillis() - CACHE_DURATION
        cachedTeamDao.deleteOldCache(oldestAllowed)

        return cachedTeamDao.getCachedTeamsCount() > 0
    }

    suspend fun clearTeamCache() {
        cachedTeamDao.clearAllTeams()
    }

    suspend fun saveSelectedTeam(teamName: String) {
        val currentPrefs = userPreferencesDao.getUserPreferencesOnce()
        if (currentPrefs != null) {
            userPreferencesDao.updateSelectedTeam(teamName)
            userPreferencesDao.markFirstLaunchComplete()
        } else {
            val newPrefs =
                UserPreferences(
                    selectedTeam = teamName,
                    isFirstLaunch = false,
                )
            userPreferencesDao.saveUserPreferences(newPrefs)
        }
    }

    fun getUserPreferences(): Flow<UserPreferences?> {
        return userPreferencesDao.getUserPreferences()
    }

    suspend fun getUserPreferencesOnce(): UserPreferences? {
        return userPreferencesDao.getUserPreferencesOnce()
    }

    suspend fun clearSelectedTeam() {
        val currentPrefs = userPreferencesDao.getUserPreferencesOnce()
        if (currentPrefs != null) {
            val updatedPrefs =
                currentPrefs.copy(
                    selectedTeam = null,
                    isFirstLaunch = true,
                )
            userPreferencesDao.saveUserPreferences(updatedPrefs)
        }
    }

    suspend fun isFirstLaunch(): Boolean {
        val prefs = userPreferencesDao.getUserPreferencesOnce()
        return prefs?.isFirstLaunch ?: true
    }
}
