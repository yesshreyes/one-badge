package com.example.one_badge.presentation.components.cards
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.one_badge.R
import com.example.one_badge.presentation.components.CardHeader
import com.example.one_badge.presentation.models.CardItem
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NextMatchCard(
    card: CardItem.NextMatch,
    modifier: Modifier = Modifier,
) {
    val match = card.match
    val countdown = matchCountdown(match.date, match.time)

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CardHeader(
            title = stringResource(R.string.card_next_match),
            subtitle = "${match.homeTeam} vs ${match.awayTeam}",
        )

        if (match.banner.isNotBlank()) {
            Spacer(Modifier.height(16.dp))

            AsyncImage(
                model = match.banner,
                contentDescription = stringResource(R.string.match_banner_desc),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(Modifier.height(16.dp))
        HorizontalDivider(Modifier.fillMaxWidth(0.9f))
        Spacer(Modifier.height(16.dp))

        if (countdown.isNotBlank()) {
            Text(
                text =
                    stringResource(
                        R.string.kickoff_in,
                        countdown,
                    ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(Modifier.height(12.dp))
        }
        Text(
            text =
                buildString {
                    appendLine(stringResource(R.string.label_league, match.league))
                    appendLine(stringResource(R.string.label_season, match.season))
                    appendLine(stringResource(R.string.label_date, match.date))
                    appendLine(stringResource(R.string.label_time, match.time))
                    appendLine(stringResource(R.string.label_venue, match.venue))
                },
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp,
        )
    }
}

fun matchCountdown(
    date: String,
    time: String,
): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val matchTime = LocalDateTime.parse("$date $time", formatter)
        val now = LocalDateTime.now()

        val duration = Duration.between(now, matchTime)

        if (duration.isNegative) {
            "Match started"
        } else {
            val days = duration.toDays()
            val hours = duration.toHours() % 24
            val minutes = duration.toMinutes() % 60

            when {
                days > 0 -> "$days days $hours hrs"
                hours > 0 -> "$hours hrs $minutes min"
                else -> "$minutes min"
            }
        }
    } catch (e: Exception) {
        ""
    }
}
