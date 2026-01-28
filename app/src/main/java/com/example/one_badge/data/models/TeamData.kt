package com.example.one_badge.data.models

data class TeamData(
    val id: String,
    val name: String,
    val shortName: String,
    val league: String,
    val country: String,
    val formedYear: String,
    val stadium: StadiumInfo,
    val keyword: String,
    val description: String,
    val socialMedia: SocialMediaLinks,
    val images: TeamImages,
    val colors: TeamColors,
    val nextMatch: MatchInfo? = null,
    val lastMatches: List<MatchInfo> = emptyList(),
    val squad: List<PlayerInfo> = emptyList(),
    val leagueTable: List<LeagueStanding> = emptyList(),
    val leagueId: String,
)

data class StadiumInfo(
    val name: String,
    val location: String,
    val capacity: String,
)

data class SocialMediaLinks(
    val website: String?,
    val facebook: String?,
    val twitter: String?,
    val instagram: String?,
    val youtube: String?,
) {
    fun hasAnyLink(): Boolean =
        !website.isNullOrBlank() ||
            !facebook.isNullOrBlank() ||
            !twitter.isNullOrBlank() ||
            !instagram.isNullOrBlank() ||
            !youtube.isNullOrBlank()
}

data class TeamImages(
    val badge: String,
    val logo: String,
    val banner: String,
    val jersey: String,
)

data class TeamColors(
    val primary: String,
    val secondary: String,
    val tertiary: String,
)

data class MatchInfo(
    val eventId: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: String?,
    val awayScore: String?,
    val date: String,
    val time: String,
    val venue: String,
    val league: String,
    val season: String,
    val banner: String,
)

data class PlayerInfo(
    val id: String,
    val name: String,
    val number: String,
    val position: String,
    val image: String,
)

data class LeagueStanding(
    val position: String,
    val teamName: String,
    val points: String,
    val badge: String,
)

fun Team.toTeamData(): TeamData =
    TeamData(
        id = idTeam ?: "",
        name = strTeam ?: "Unknown Team",
        shortName = strTeamShort ?: "",
        league = strLeague ?: "",
        country = strCountry ?: "",
        formedYear = intFormedYear ?: "N/A",
        stadium =
            StadiumInfo(
                name = strStadium ?: "N/A",
                location = strLocation ?: "N/A",
                capacity = intStadiumCapacity ?: "N/A",
            ),
        description = strDescriptionEN ?: "",
        socialMedia =
            SocialMediaLinks(
                website = strWebsite?.takeIf { it.isNotBlank() },
                facebook = strFacebook?.takeIf { it.isNotBlank() },
                twitter = strTwitter?.takeIf { it.isNotBlank() },
                instagram = strInstagram?.takeIf { it.isNotBlank() },
                youtube = strYoutube?.takeIf { it.isNotBlank() },
            ),
        images =
            TeamImages(
                badge = strBadge ?: "",
                logo = strLogo ?: "",
                banner = strBanner ?: "",
                jersey = strEquipment ?: "",
            ),
        colors =
            TeamColors(
                primary = strColour1 ?: "",
                secondary = strColour2 ?: "",
                tertiary = strColour3 ?: "",
            ),
        leagueId = idLeague ?: "",
        keyword = strKeywords ?: "",
    )

// Event → MatchInfo
fun Event.toMatchInfo(): MatchInfo =
    MatchInfo(
        eventId = idEvent ?: "",
        homeTeam = strHomeTeam ?: "",
        awayTeam = strAwayTeam ?: "",
        homeScore = intHomeScore,
        awayScore = intAwayScore,
        date = dateEvent ?: "",
        time = strTime ?: "",
        venue = strVenue ?: "",
        league = strLeague ?: "",
        season = strSeason ?: "",
        banner = strThumb ?: strFanart ?: "",
    )

// Player → PlayerInfo
fun Player.toPlayerInfo(): PlayerInfo =
    PlayerInfo(
        id = idPlayer ?: "",
        name = strPlayer ?: "",
        number = strNumber ?: "",
        position = strPosition ?: "",
        image = strThumb ?: "",
    )

fun TableTeam.toLeagueStanding(): LeagueStanding =
    LeagueStanding(
        position = intRank ?: "",
        teamName = strTeam ?: "",
        points = intPoints ?: "",
        badge = strBadge ?: "",
    )
