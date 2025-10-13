package com.example.one_badge.ui.screens.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_badge.data.models.CardData
import com.example.one_badge.data.models.TeamCard
import com.example.one_badge.data.models.TeamCardData
import com.example.one_badge.data.models.toTeamCard
import com.example.one_badge.data.models.toTeamCardData
import com.example.one_badge.data.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val teamData: TeamCardData? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val logoUrl: String get() = teamData?.logoUrl ?: ""
    val bannerUrl: String get() = teamData?.bannerUrl ?: ""

    val primaryColor: Color
        get() =
            teamData?.let {
                try {
                    Color(android.graphics.Color.parseColor(it.color1))
                } catch (e: Exception) {
                    Color.White
                }
            } ?: Color.White

    val secondaryColor: Color
        get() =
            teamData?.let {
                try {
                    Color(android.graphics.Color.parseColor(it.color2))
                } catch (e: Exception) {
                    Color.White
                }
            } ?: Color.White

    val accentColor: Color
        get() =
            teamData?.let {
                if (it.color3.isNotBlank()) {
                    try {
                        Color(android.graphics.Color.parseColor(it.color3))
                    } catch (e: Exception) {
                        Color.White
                    }
                } else {
                    Color.White
                }
            } ?: Color.White

    val cards: List<TeamCard> get() =
        teamData?.let { data ->
            buildList {
                add(CardData.TeamInfo(data).toTeamCard())

                if (data.description.isNotBlank()) {
                    add(CardData.Description(data).toTeamCard())
                }

                if (data.jersey.isNotBlank()) {
                    add(CardData.Jersey(data).toTeamCard())
                }

                add(CardData.SocialMedia(data).toTeamCard())
            }
        } ?: emptyList()
}

class HomeViewModel(private val repository: TeamRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private var currentTeamName: String = ""

    fun fetchTeamData(
        teamName: String,
        forceRefresh: Boolean = false,
    ) {
        currentTeamName = teamName
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val team = repository.getTeamByName(teamName).teams?.firstOrNull()
                team?.let {
                    _uiState.value =
                        HomeUiState(
                            teamData = it.toTeamCardData(),
                            isLoading = false,
                        )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to load team data",
                    )
            }
        }
    }

    fun refreshContent() {
        fetchTeamData(currentTeamName, forceRefresh = true)
    }
}
