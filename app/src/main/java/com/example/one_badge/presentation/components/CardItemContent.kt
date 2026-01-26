package com.example.one_badge.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.one_badge.presentation.components.cards.DescriptionCard
import com.example.one_badge.presentation.components.cards.JerseyCard
import com.example.one_badge.presentation.components.cards.LeagueTableCard
import com.example.one_badge.presentation.components.cards.NextMatchCard
import com.example.one_badge.presentation.components.cards.PrevMatchCard
import com.example.one_badge.presentation.components.cards.SocialMediaCard
import com.example.one_badge.presentation.components.cards.SquadCard
import com.example.one_badge.presentation.components.cards.TeamInfoCard
import com.example.one_badge.presentation.models.CardItem

/**
 * Dispatcher composable that renders the appropriate card based on the CardItem type.
 * This uses Kotlin's when expression for exhaustive type checking.
 */
@Composable
fun CardItemContent(
    item: CardItem,
    modifier: Modifier = Modifier,
) {
    when (item) {
        is CardItem.TeamInfo -> TeamInfoCard(item, modifier)
        is CardItem.Description -> DescriptionCard(item, modifier)
        is CardItem.Jersey -> JerseyCard(item, modifier)
        is CardItem.SocialMedia -> SocialMediaCard(item, modifier)
        is CardItem.NextMatch -> NextMatchCard(item)
        is CardItem.PreviousMatches -> PrevMatchCard(item)
        is CardItem.Squad -> SquadCard(item)
        is CardItem.LeagueTable -> LeagueTableCard(item)
    }
}
