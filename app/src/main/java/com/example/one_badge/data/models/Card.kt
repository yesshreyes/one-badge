package com.example.one_badge.data.models

sealed class CardData {
    data class TeamInfo(val data: TeamCardData) : CardData()

    data class Description(val data: TeamCardData) : CardData()

    data class Jersey(val data: TeamCardData) : CardData()

    data class SocialMedia(val data: TeamCardData) : CardData()
}

data class TeamCard(
    val id: Long,
    val title: String,
    val subtitle: String,
    val details: String,
    val badgeUrl: String = "",
    val jerseyImageUrl: String = "",
)

fun CardData.toTeamCard(): TeamCard =
    when (this) {
        is CardData.TeamInfo ->
            TeamCard(
                id = 1L,
                title = "Team Info",
                subtitle = data.teamName,
                details =
                    buildString {
                        appendLine("Short Name: ${data.teamShort}")
                        appendLine("League: ${data.league}")
                        appendLine("Country: ${data.country}")
                        appendLine("Formed: ${data.formedYear}")
                        appendLine("Stadium: ${data.stadium}")
                        appendLine("Location: ${data.location}")
                        append("Capacity: ${data.stadiumCapacity}")
                    },
                badgeUrl = data.badgeUrl,
            )

        is CardData.Description ->
            TeamCard(
                id = 2L,
                title = "Description",
                subtitle = data.teamName,
                details = data.description.ifBlank { "No description available" },
                badgeUrl = "",
            )

        is CardData.Jersey ->
            TeamCard(
                id = 3L,
                title = "Jersey",
                subtitle = data.teamName,
                details = "",
                badgeUrl = "",
                jerseyImageUrl = data.jersey,
            )

        is CardData.SocialMedia ->
            TeamCard(
                id = 4L,
                title = "Social Media",
                subtitle = data.teamName,
                details =
                    buildString {
                        if (data.facebook.isNotBlank()) appendLine("Facebook\n${data.facebook}")
                        if (data.twitter.isNotBlank()) appendLine("Twitter\n${data.twitter}")
                        if (data.instagram.isNotBlank()) appendLine("Instagram\n${data.instagram}")
                        if (data.youtube.isNotBlank()) append("YouTube\n${data.youtube}")
                    },
                badgeUrl = "",
            )
    }
