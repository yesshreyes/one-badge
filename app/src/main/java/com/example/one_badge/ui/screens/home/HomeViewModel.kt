package com.example.one_badge.ui.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_badge.data.models.toTeamCardData
import com.example.one_badge.data.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val repository: TeamRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val teamName: String =
            savedStateHandle.get<String>("teamName")
                ?: error("teamName missing")

        private val _uiState = MutableStateFlow(HomeUiState())
        val uiState: StateFlow<HomeUiState> = _uiState

        init {
            fetchTeamData()
        }

        private fun fetchTeamData(forceRefresh: Boolean = false) {
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
            fetchTeamData(forceRefresh = true)
        }
    }
