package com.example.one_badge.ui.screens.home

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.one_badge.data.models.CardData
import com.example.one_badge.data.models.TeamCard
import com.example.one_badge.data.models.TeamCardData
import com.example.one_badge.data.models.toTeamCard

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
                    Color(it.color1.toColorInt())
                } catch (e: Exception) {
                    Color.White
                }
            } ?: Color.White

    val secondaryColor: Color
        get() =
            teamData?.let {
                try {
                    Color(it.color2.toColorInt())
                } catch (e: Exception) {
                    Color.White
                }
            } ?: Color.White

    val accentColor: Color
        get() =
            teamData?.let {
                if (it.color3.isNotBlank()) {
                    try {
                        Color(it.color3.toColorInt())
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
