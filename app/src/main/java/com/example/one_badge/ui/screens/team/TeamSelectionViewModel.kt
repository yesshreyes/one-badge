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

    fun loadTeams() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val teamsWithBadges = mutableListOf<TeamSelectionItem>()

                teamNames.forEach { teamName ->
                    try {
                        val response = repository.getTeamByName(teamName)
                        val team = response.teams?.firstOrNull()

                        teamsWithBadges.add(
                            TeamSelectionItem(
                                name = teamName,
                                badgeUrl = team?.strBadge ?: "",
                            ),
                        )
                    } catch (e: Exception) {
                        teamsWithBadges.add(
                            TeamSelectionItem(
                                name = teamName,
                                badgeUrl = "",
                            ),
                        )
                    }
                }

                _uiState.value =
                    _uiState.value.copy(
                        teams = teamsWithBadges.sortedBy { it.name },
                        isLoading = false,
                    )
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to load teams",
                    )
            }
        }
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
}
