package com.example.one_badge.data.models

data class TeamResponse(
    val teams: List<Team>?
)

data class Team(
    val idTeam: String?,
    val strTeam: String?,
    val strLogo: String?,
    val strBadge: String?,
    val intFormedYear: String?,
    val strStadium: String?,
    val strLocation: String?,
    val intStadiumCapacity: String?,
    val strDescriptionEN: String?,
    val strEquipment: String?,
    val strFacebook: String?,
    val strTwitter: String?,
    val strInstagram: String?,
    val strYoutube: String?
)


data class TeamInfoCard(
    val teamName: String,
    val formedYear: String,
    val stadium: String,
    val location: String,
    val stadiumCapacity: String,
    val badgeUrl: String,
    val logoUrl: String
)

data class SocialMediaCard(
    val facebook: String,
    val twitter: String,
    val instagram: String,
    val youtube: String
)
