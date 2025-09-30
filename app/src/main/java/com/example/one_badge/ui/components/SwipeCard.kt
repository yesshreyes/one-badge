package com.example.one_badge.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.one_badge.data.models.TeamCard
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import androidx.core.net.toUri
import com.example.one_badge.R

enum class Direction { LEFT, RIGHT }

@Composable
fun SwipeCard(
    card: TeamCard,
    onSwiped: (Direction) -> Unit,
    modifier: Modifier = Modifier
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
        modifier = modifier
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
                    }
                )
            }
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .graphicsLayer { rotationZ = rotation }
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
    ) {
        CardContent(card = card)
    }
}

@Composable
fun CardContent(card: TeamCard, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Make ALL cards scrollable
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(card.title, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(6.dp))
        Text(card.subtitle, style = MaterialTheme.typography.bodyMedium)

        if (card.badgeUrl.isNotBlank()) {
            Spacer(Modifier.height(12.dp))
            AsyncImage(
                model = card.badgeUrl,
                contentDescription = "Team Badge",
                modifier = Modifier.size(80.dp)
            )
        }

        if (card.jerseyImageUrl.isNotBlank()) {
            Spacer(Modifier.height(12.dp))
            AsyncImage(
                model = card.jerseyImageUrl,
                contentDescription = "Team Equipment",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(Modifier.height(16.dp))

        // Special handling for Social Media card
        if (card.id == 4L && card.title == "Social Media") {
            SocialMediaLinks(card.details)
        } else {
            Text(
                text = card.details,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SocialMediaLinks(details: String) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        if (details.contains("Facebook")) {
            val facebookUrl = extractUrl(details, "Facebook")
            ClickableSocialItem(
                iconRes = R.drawable.facebook,
                text = "Facebook",
                onClick = { openUrl(context, "https://$facebookUrl") }
            )
        }
        if (details.contains("Twitter")) {
            val twitterUrl = extractUrl(details, "Twitter")
            ClickableSocialItem(
                iconRes = R.drawable.x,
                text = "Twitter",
                onClick = { openUrl(context, "https://$twitterUrl") }
            )
        }
        if (details.contains("Instagram")) {
            val instagramUrl = extractUrl(details, "Instagram")
            ClickableSocialItem(
                iconRes = R.drawable.instagram,
                text = "Instagram",
                onClick = { openUrl(context, "https://$instagramUrl") }
            )
        }
        if (details.contains("YouTube")) {
            val youtubeUrl = extractUrl(details, "YouTube")
            ClickableSocialItem(
                iconRes = R.drawable.youtube,
                text = "YouTube",
                onClick = { openUrl(context, "https://$youtubeUrl") }
            )
        }
    }
}

@Composable
private fun ClickableSocialItem(
    iconRes: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "$text Logo",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

private fun extractUrl(details: String, platform: String): String {
    val lines = details.split("\n")
    val platformLine = lines.find { it.contains(platform) }
    return platformLine?.substringAfter("$platform\n") ?: ""
}

private fun openUrl(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}
