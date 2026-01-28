package com.one_badge.app.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.one_badge.app.presentation.models.CardItem

/**
 * Legacy wrapper for CardContent that delegates to the new CardItemContent.
 * This maintains backward compatibility with existing code.
 */
@Composable
fun CardContent(
    card: CardItem,
    modifier: Modifier = Modifier,
) {
    CardItemContent(item = card, modifier = modifier)
}
