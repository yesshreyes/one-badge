package com.example.one_badge.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_badge.data.models.*
import com.example.one_badge.data.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val teamData: TeamCardData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val logoUrl: String get() = teamData?.logoUrl ?: ""
    val bannerUrl: String get() = teamData?.bannerUrl ?: ""

    val cards: List<TeamCard> get() = teamData?.let { data ->
        buildList {
            add(CardData.TeamInfo(data).toTeamCard())

            if (data.description.isNotBlank()) {
                add(CardData.Description(data).toTeamCard())
            }

            if (data.jersey.isNotBlank()) {
                add(CardData.Jersey(data).toTeamCard())
            }

            // Always add social media card - let the card itself handle empty state
            add(CardData.SocialMedia(data).toTeamCard())
        }
    } ?: emptyList()


}

class HomeViewModel(private val repository: TeamRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun fetchTeamData(teamName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val team = repository.getTeamByName(teamName).teams?.firstOrNull()
                team?.let {
                    _uiState.value = HomeUiState(
                        teamData = it.toTeamCardData(),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching team data", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load team data"
                )
            }
        }
    }
}
