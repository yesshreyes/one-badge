package com.example.one_badge.ui.screens.team

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_badge.data.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TeamSelectionItem(
    val name: String,
    val badgeUrl: String
)

data class TeamSelectionUiState(
    val teams: List<TeamSelectionItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class TeamSelectionViewModel(
    private val repository: TeamRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeamSelectionUiState())
    val uiState: StateFlow<TeamSelectionUiState> = _uiState

    private val teamNames = listOf(
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
        "Borussia Dortmund"
    )

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
                                badgeUrl = team?.strBadge ?: ""
                            )
                        )

                        Log.d("TeamSelectionVM", "Loaded $teamName with badge: ${team?.strBadge}")

                    } catch (e: Exception) {
                        Log.e("TeamSelectionVM", "Failed to load $teamName", e)
                        // Add team without badge if API call fails
                        teamsWithBadges.add(
                            TeamSelectionItem(
                                name = teamName,
                                badgeUrl = ""
                            )
                        )
                    }
                }

                _uiState.value = TeamSelectionUiState(
                    teams = teamsWithBadges.sortedBy { it.name },
                    isLoading = false
                )

            } catch (e: Exception) {
                Log.e("TeamSelectionVM", "Error loading teams", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load teams"
                )
            }
        }
    }
}
