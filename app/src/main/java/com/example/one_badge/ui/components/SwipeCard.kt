package com.example.one_badge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.one_badge.data.models.TeamCard
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

enum class Direction { LEFT, RIGHT }

@Composable
fun SwipeCard(
    card: TeamCard,
    onSwiped: (Direction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    val offsetX = remember { androidx.compose.animation.core.Animatable(0f) }
    val offsetY = remember { androidx.compose.animation.core.Animatable(0f) }

    val rotation by remember {
        derivedStateOf {
            (offsetX.value / 20f).coerceIn(-40f, 40f)
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .pointerInput(card.id) {
                    detectDragGestures(
                        onDrag = { _, dragAmount ->
                            scope.launch {
                                offsetX.snapTo(offsetX.value + dragAmount.x)
                                offsetY.snapTo(offsetY.value + dragAmount.y)
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                val threshold = 300f
                                if (abs(offsetX.value) > threshold) {
                                    val direction = if (offsetX.value > 0) Direction.RIGHT else Direction.LEFT
                                    val targetX = if (direction == Direction.RIGHT) 1000f else -1000f
                                    offsetX.animateTo(targetX)
                                    onSwiped(direction)
                                } else {
                                    offsetX.animateTo(0f)
                                    offsetY.animateTo(0f)
                                }
                            }
                        },
                    )
                }
                .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
                .graphicsLayer { rotationZ = rotation }
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false,
                )
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp),
    ) {
        CardContent(card = card)
    }
}
