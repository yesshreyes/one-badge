package com.example.one_badge.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.one_badge.presentation.components.cards.CardContent
import com.example.one_badge.presentation.models.CardItem
import kotlin.math.absoluteValue

fun lerp(
    start: Float,
    stop: Float,
    fraction: Float,
): Float {
    return start + fraction * (stop - start)
}

@Composable
fun Carousel(
    cards: List<CardItem>,
    onCardClick: (CardItem) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { cards.size })

    Column {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 86.dp),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(420.dp),
            pageSpacing = 16.dp,
        ) { page ->
            val card = cards[page]
            val pageOffset =
                (
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                ).absoluteValue.coerceIn(0f, 1f)

            val scale = lerp(0.85f, 1.05f, 1f - pageOffset)
            val alpha = lerp(0.6f, 1f, 1f - pageOffset)
            val shadow = lerp(2f, 28f, 1f - pageOffset)

            Box(
                modifier =
                    Modifier
                        .width(280.dp)
                        .aspectRatio(3f / 4f)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                        .shadow(
                            elevation = shadow.dp,
                            shape = RoundedCornerShape(24.dp),
                            clip = false,
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable(onClick = { onCardClick(card) }),
            ) {
                CardContent(card)
            }
        }
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            cards.indices.forEachIndexed { index, i ->
                val isSelected = i == pagerState.currentPage
                Box(
                    modifier =
                        Modifier
                            .size(if (isSelected) 10.dp else 6.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .background(
                                if (isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                },
                            ),
                )
                if (index < cards.lastIndex) {
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}
