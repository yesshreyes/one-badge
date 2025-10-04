package com.example.one_badge.ui.screens.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_badge.data.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TeamSelectionItem(
    val name: String,
    val badgeUrl: String,
)

data class TeamSelectionUiState(
    val teams: List<TeamSelectionItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedTeam: String? = null,
    val isFromCache: Boolean = false,
)

class TeamSelectionViewModel(
    private val repository: TeamRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TeamSelectionUiState())
    val uiState: StateFlow<TeamSelectionUiState> = _uiState

    private val teamNames =
        listOf(
            "Arsenal",
            "Chelsea",
            "Liverpool",
            "Manchester City",
            "Manchester United",
            "Tottenham Hotspur",
            "Real Madrid",
            "Barcelona",
            "Bayern Munich",
            "PSG",
            "Juventus",
            "AC Milan",
            "Inter Milan",
            "Atletico Madrid",
            "Borussia Dortmund",
        )

    init {
        viewModelScope.launch {
            val prefs = repository.getUserPreferencesOnce()
            if (prefs?.selectedTeam != null && !prefs.isFirstLaunch) {
                _uiState.value = _uiState.value.copy(selectedTeam = prefs.selectedTeam)
            }
        }
    }

    fun loadTeams(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                if (!forceRefresh && repository.isCacheValid()) {
                    loadFromCache()
                } else {
                    loadFromNetwork()
                }
            } catch (e: Exception) {
                val cachedTeams = repository.getCachedTeams()
                if (cachedTeams.isNotEmpty()) {
                    loadFromCache()
                } else {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            error = "Failed to load teams",
                        )
                }
            }
        }
    }

    private suspend fun loadFromCache() {
        val cachedTeams = repository.getCachedTeams()
        val teams =
            cachedTeams.map { cached ->
                TeamSelectionItem(
                    name = cached.teamName,
                    badgeUrl = cached.badgeUrl,
                )
            }
        _uiState.value =
            _uiState.value.copy(
                teams = teams,
                isLoading = false,
                isFromCache = true,
            )
    }

    private suspend fun loadFromNetwork() {
        val teamsWithBadges = mutableListOf<TeamSelectionItem>()
        val teamsToCache = mutableListOf<Pair<String, String>>()

        teamNames.forEach { teamName ->
            try {
                val response = repository.getTeamByName(teamName)
                val team = response.teams?.firstOrNull()
                val badgeUrl = team?.strBadge ?: ""

                teamsWithBadges.add(
                    TeamSelectionItem(
                        name = teamName,
                        badgeUrl = badgeUrl,
                    ),
                )

                teamsToCache.add(teamName to badgeUrl)
            } catch (e: Exception) {
                teamsWithBadges.add(
                    TeamSelectionItem(
                        name = teamName,
                        badgeUrl = "",
                    ),
                )
                teamsToCache.add(teamName to "")
            }
        }

        repository.cacheTeams(teamsToCache)

        _uiState.value =
            _uiState.value.copy(
                teams = teamsWithBadges.sortedBy { it.name },
                isLoading = false,
                isFromCache = false,
            )
    }

    fun selectTeam(teamName: String) {
        viewModelScope.launch {
            try {
                repository.saveSelectedTeam(teamName)
                _uiState.value = _uiState.value.copy(selectedTeam = teamName)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Failed to save team selection")
            }
        }
    }

    fun clearTeamSelection() {
        viewModelScope.launch {
            try {
                repository.clearSelectedTeam()
                _uiState.value = _uiState.value.copy(selectedTeam = null)
            } catch (e: Exception) {
            }
        }
    }

    fun refreshTeams() {
        loadTeams(forceRefresh = true)
    }
}
