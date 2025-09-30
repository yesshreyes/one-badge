package com.example.one_badge.data.models

data class TeamResponse(
    val teams: List<Team>? = null
)

data class Team(
    val idTeam: String? = null,
    val strTeam: String? = null,
    val strTeamShort: String? = null,
    val strLeague: String? = null,
    val strCountry: String? = null,
    val strLogo: String? = null,
    val strBadge: String? = null,
    val strBanner: String? = null,
    val intFormedYear: String? = null,
    val strStadium: String? = null,
    val strLocation: String? = null,
    val intStadiumCapacity: String? = null,
    val strDescriptionEN: String? = null,
    val strEquipment: String? = null,
    val strFacebook: String? = null,
    val strTwitter: String? = null,
    val strInstagram: String? = null,
    val strYoutube: String? = null
)

data class TeamCardData(
    val teamName: String = "",
    val teamShort: String = "",
    val league: String = "",
    val country: String = "",
    val formedYear: String = "N/A",
    val stadium: String = "N/A",
    val location: String = "N/A",
    val stadiumCapacity: String = "N/A",
    val description: String = "",
    val jersey: String = "",
    val facebook: String = "",
    val twitter: String = "",
    val instagram: String = "",
    val youtube: String = "",
    val badgeUrl: String = "",
    val logoUrl: String = "",
    val bannerUrl: String = ""
)

fun Team.toTeamCardData(): TeamCardData = TeamCardData(
    teamName = strTeam ?: "Unknown Team",
    teamShort = strTeamShort ?: "",
    league = strLeague ?: "",
    country = strCountry ?: "",
    formedYear = intFormedYear ?: "N/A",
    stadium = strStadium ?: "N/A",
    location = strLocation ?: "N/A",
    stadiumCapacity = intStadiumCapacity ?: "N/A",
    description = strDescriptionEN ?: "",
    jersey = strEquipment ?: "",
    facebook = strFacebook ?: "",
    twitter = strTwitter ?: "",
    instagram = strInstagram ?: "",
    youtube = strYoutube ?: "",
    badgeUrl = strBadge ?: "",
    logoUrl = strLogo ?: "",
    bannerUrl = strBanner ?: ""
)
