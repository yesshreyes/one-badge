package com.one_badge.app.presentation.screens.home

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.one_badge.app.data.models.TeamData
import com.one_badge.app.presentation.models.CardItem
import com.one_badge.app.presentation.models.toCardItems

data class HomeUiState(
    val teamData: TeamData? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val logoUrl: String get() = teamData?.images?.logo ?: ""
    val bannerUrl: String get() = teamData?.images?.banner ?: ""

    val primaryColor: Color
        get() =
            teamData?.let {
                try {
                    Color(it.colors.primary.toColorInt())
                } catch (e: Exception) {
                    Color.White
                }
            } ?: Color.White

    val secondaryColor: Color
        get() =
            teamData?.let {
                try {
                    Color(it.colors.secondary.toColorInt())
                } catch (e: Exception) {
                    Color.White
                }
            } ?: Color.White

    val accentColor: Color
        get() =
            teamData?.let {
                if (it.colors.tertiary.isNotBlank()) {
                    try {
                        Color(it.colors.tertiary.toColorInt())
                    } catch (e: Exception) {
                        Color.White
                    }
                } else {
                    Color.White
                }
            } ?: Color.White

    val cards: List<CardItem> get() = teamData?.toCardItems() ?: emptyList()
}
