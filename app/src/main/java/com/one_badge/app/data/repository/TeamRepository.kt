package com.one_badge.app.data.repository

import com.one_badge.app.data.local.cachedTeam.CachedTeam
import com.one_badge.app.data.local.cachedTeam.CachedTeamDao
import com.one_badge.app.data.local.userPreferences.UserPreferences
import com.one_badge.app.data.local.userPreferences.UserPreferencesDao
import com.one_badge.app.data.models.Team
import com.one_badge.app.data.models.TeamData
import com.one_badge.app.data.models.TeamResponse
import com.one_badge.app.data.models.toLeagueStanding
import com.one_badge.app.data.models.toMatchInfo
import com.one_badge.app.data.models.toPlayerInfo
import com.one_badge.app.data.models.toTeamData
import com.one_badge.app.data.remote.SportsDbApi
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
        return api.searchTeams(teamName = teamName)
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

    suspend fun getTeamExtras(team: Team): TeamData {
        val teamId = team.idTeam ?: return team.toTeamData()

        val next = api.getNextMatches(teamId = teamId)
        val last = api.getLastMatches(teamId = teamId)
        val players = api.getPlayers(teamId = teamId)
        val table =
            if (team.idLeague != null) {
                api.getLeagueTable(
                    leagueId = team.idLeague,
                    season = "2025-2026",
                )
            } else {
                null
            }

        return team.toTeamData().copy(
            nextMatch = next.events?.firstOrNull()?.toMatchInfo(),
            lastMatches = last.results?.map { it.toMatchInfo() } ?: emptyList(),
            squad = players.player?.map { it.toPlayerInfo() } ?: emptyList(),
            leagueTable = table?.table?.map { it.toLeagueStanding() } ?: emptyList(),
        )
    }
}
