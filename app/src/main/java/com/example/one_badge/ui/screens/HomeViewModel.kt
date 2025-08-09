package com.example.one_badge.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_badge.data.models.SocialMediaCard
import com.example.one_badge.data.models.TeamInfoCard
import com.example.one_badge.data.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TeamRepository) : ViewModel() {

    private val _logoUrl = MutableStateFlow<String?>(null)
    val logoUrl: StateFlow<String?> = _logoUrl

    private val _badgeUrl = MutableStateFlow<String?>(null)
    val badgeUrl: StateFlow<String?> = _badgeUrl

    private val _teamInfo = MutableStateFlow<TeamInfoCard?>(null)
    val teamInfo: StateFlow<TeamInfoCard?> = _teamInfo

    private val _teamDescription = MutableStateFlow<String?>(null)
    val teamDescription: StateFlow<String?> = _teamDescription

    private val _teamEquipment = MutableStateFlow<String?>(null)
    val teamEquipment: StateFlow<String?> = _teamEquipment

    private val _socialMedia = MutableStateFlow<SocialMediaCard?>(null)
    val socialMedia: StateFlow<SocialMediaCard?> = _socialMedia


    fun fetchTeamData(teamName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTeamByName(teamName)
                val team = response.teams?.firstOrNull()

                team?.let {
                    _logoUrl.value = it.strLogo
                    _badgeUrl.value = it.strBadge

                    _teamInfo.value = TeamInfoCard(
                        teamName = it.strTeam ?: "Unknown",
                        formedYear = it.intFormedYear ?: "N/A",
                        stadium = it.strStadium ?: "N/A",
                        location = it.strLocation ?: "N/A",
                        stadiumCapacity = it.intStadiumCapacity ?: "N/A",
                        badgeUrl = it.strBadge ?: "",
                        logoUrl = it.strLogo ?: ""
                    )

                    _teamDescription.value = it.strDescriptionEN ?: "No description available."
                    _teamEquipment.value = it.strEquipment ?: ""
                    _socialMedia.value = SocialMediaCard(
                        facebook = it.strFacebook ?: "",
                        twitter = it.strTwitter ?: "",
                        instagram = it.strInstagram ?: "",
                        youtube = it.strYoutube ?: ""
                    )
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching team data", e)
            }
        }
    }
}
