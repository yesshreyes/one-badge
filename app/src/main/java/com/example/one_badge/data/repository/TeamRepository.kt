package com.example.one_badge.data.repository

import com.example.one_badge.data.local.UserPreferences
import com.example.one_badge.data.local.UserPreferencesDao
import com.example.one_badge.data.models.TeamResponse
import com.example.one_badge.data.remote.SportsDbApi
import kotlinx.coroutines.flow.Flow

class TeamRepository(
    private val api: SportsDbApi,
    private val userPreferencesDao: UserPreferencesDao
) {
    suspend fun getTeamByName(teamName: String): TeamResponse {
        return api.searchTeams(teamName)
    }

    suspend fun saveSelectedTeam(teamName: String) {
        val currentPrefs = userPreferencesDao.getUserPreferencesOnce()
        if (currentPrefs != null) {
            userPreferencesDao.updateSelectedTeam(teamName)
            userPreferencesDao.markFirstLaunchComplete()
        } else {
            val newPrefs = UserPreferences(
                selectedTeam = teamName,
                isFirstLaunch = false
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
            val updatedPrefs = currentPrefs.copy(
                selectedTeam = null,
                isFirstLaunch = true
            )
            userPreferencesDao.saveUserPreferences(updatedPrefs)
        }
    }

    suspend fun isFirstLaunch(): Boolean {
        val prefs = userPreferencesDao.getUserPreferencesOnce()
        return prefs?.isFirstLaunch ?: true
    }
}
