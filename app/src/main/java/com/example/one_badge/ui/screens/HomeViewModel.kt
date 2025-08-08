package com.example.one_badge.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_badge.data.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TeamRepository) : ViewModel() {
    private val _logoUrl = MutableStateFlow<String?>(null)
    val logoUrl: StateFlow<String?> = _logoUrl

    fun fetchTeamLogo(teamName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTeamByName(teamName)
                Log.d("HomeViewModel", "API Response: $response")  // Log whole response

                val logo = response.teams?.firstOrNull()?.strTeamLogo ?: response.teams?.firstOrNull()?.strTeamBadge
                Log.d("HomeViewModel", "Extracted logo URL: $logo")  // Log logo URL

                _logoUrl.value = logo
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching team logo", e)
                _logoUrl.value = null
            }
        }
    }

}

