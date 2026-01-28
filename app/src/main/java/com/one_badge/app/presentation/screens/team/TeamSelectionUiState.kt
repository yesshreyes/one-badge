package com.one_badge.app.presentation.screens.team

data class TeamSelectionUiState(
    val teams: List<TeamSelectionItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedTeam: String? = null,
    val isFromCache: Boolean = false,
    val searchQuery: String = "",
)
