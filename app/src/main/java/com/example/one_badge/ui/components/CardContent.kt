package com.example.one_badge.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.one_badge.data.models.TeamCard

@Composable
fun CardContent(card: TeamCard, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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