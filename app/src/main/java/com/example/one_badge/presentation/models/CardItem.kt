package com.example.one_badge.presentation.models

import com.example.one_badge.data.models.SocialMediaLinks
import com.example.one_badge.data.models.StadiumInfo
import com.example.one_badge.data.models.TeamData

/**
 * Sealed interface representing different types of cards that can be displayed.
 * Each card type contains only the data it needs to render.
 */
sealed interface CardItem {
    val id: String

    data class TeamInfo(
        override val id: String = "team_info",
        val teamName: String,
        val shortName: String,
        val league: String,
        val country: String,
        val formedYear: String,
        val stadium: StadiumInfo,
        val badgeUrl: String,
    ) : CardItem

    data class Description(
        override val id: String = "description",
        val teamName: String,
        val description: String,
    ) : CardItem

    data class Jersey(
        override val id: String = "jersey",
        val teamName: String,
        val jerseyImageUrl: String,
    ) : CardItem

    data class SocialMedia(
        override val id: String = "social_media",
        val teamName: String,
        val links: SocialMediaLinks,
    ) : CardItem
}

/**
 * Extension function to convert TeamData to a list of CardItems.
 * Only includes cards that have valid data.
 */
fun TeamData.toCardItems(): List<CardItem> =
    buildList {
        // Always include team info
        add(
            CardItem.TeamInfo(
                teamName = name,
                shortName = shortName,
                league = league,
                country = country,
                formedYear = formedYear,
                stadium = stadium,
                badgeUrl = images.badge,
            ),
        )

        // Always include description (with fallback text)
        add(
            CardItem.Description(
                teamName = name,
                description = description.ifBlank { "No description available" },
            ),
        )

        // Only include jersey if image is available
        if (images.jersey.isNotBlank()) {
            add(
                CardItem.Jersey(
                    teamName = name,
                    jerseyImageUrl = images.jersey,
                ),
            )
        }

        // Only include social media if at least one link exists
        if (socialMedia.hasAnyLink()) {
            add(
                CardItem.SocialMedia(
                    teamName = name,
                    links = socialMedia,
                ),
            )
        }
    }
