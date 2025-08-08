package com.example.one_badge.data.models

sealed class CardType {
    object Info: CardType()
    object NextMatch: CardType()
    object LastMatch: CardType()
    object TopScorer: CardType()
    object LeagueStanding: CardType()
}

data class BadgeCard(
    val id: Long,
    val type: CardType,
    val title: String,
    val subtitle: String,
    val details: String = "",
)

val sampleCards = listOf(
    BadgeCard(1, CardType.Info, "Established", "jersey", "Location"),
    BadgeCard(2, CardType.NextMatch, "Next Match", "Real Madrid vs Barcelona", "Sat • 7:30 PM"),
    BadgeCard(3, CardType.LastMatch, "Last Match", "Real Madrid 2 - 1 Juventus", "Highlights available"),
    BadgeCard(4, CardType.TopScorer, "Top Scorer", "Ronaldo — 12 goals", "Season"),
    BadgeCard(5, CardType.LeagueStanding, "League Table", "Team A — 4th (32 pts)", "View details"),
)