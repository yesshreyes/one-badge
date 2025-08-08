package com.example.one_badge.data.models

data class TeamResponse(
    val teams: List<Team>?
)

data class Team(
    val idTeam: String,
    val strTeam: String,
    val strTeamBadge: String?,  // team badge URL
    val strTeamLogo: String?    // team logo URL (usually larger)
)
