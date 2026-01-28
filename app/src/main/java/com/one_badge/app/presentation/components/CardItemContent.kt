package com.one_badge.app.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.one_badge.app.presentation.components.cards.DescriptionCard
import com.one_badge.app.presentation.components.cards.JerseyCard
import com.one_badge.app.presentation.components.cards.LeagueTableCard
import com.one_badge.app.presentation.components.cards.NextMatchCard
import com.one_badge.app.presentation.components.cards.PrevMatchCard
import com.one_badge.app.presentation.components.cards.SocialMediaCard
import com.one_badge.app.presentation.components.cards.SquadCard
import com.one_badge.app.presentation.components.cards.TeamInfoCard
import com.one_badge.app.presentation.models.CardItem

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
        is CardItem.PreviousMatches -> PrevMatchCard(item)
        is CardItem.NextMatch -> NextMatchCard(item)
        is CardItem.LeagueTable -> LeagueTableCard(item)
        is CardItem.Squad -> SquadCard(item)
        is CardItem.Jersey -> JerseyCard(item, modifier)
        is CardItem.SocialMedia -> SocialMediaCard(item, modifier)
    }
}
