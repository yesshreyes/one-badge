package com.example.one_badge.data.models

/**
 * Clean domain model representing team data.
 * This is the single source of truth for team information in the app.
 */
data class TeamData(
    val id: String,
    val name: String,
    val shortName: String,
    val league: String,
    val country: String,
    val formedYear: String,
    val stadium: StadiumInfo,
    val description: String,
    val socialMedia: SocialMediaLinks,
    val images: TeamImages,
    val colors: TeamColors,
)

data class StadiumInfo(
    val name: String,
    val location: String,
    val capacity: String,
)

data class SocialMediaLinks(
    val facebook: String?,
    val twitter: String?,
    val instagram: String?,
    val youtube: String?,
) {
    fun hasAnyLink(): Boolean =
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

/**
 * Extension function to convert API Team model to domain TeamData model.
 */
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
    )
