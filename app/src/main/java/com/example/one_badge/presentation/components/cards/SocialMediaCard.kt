package com.example.one_badge.presentation.components.cards

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.one_badge.R
import com.example.one_badge.presentation.components.CardHeader
import com.example.one_badge.presentation.models.CardItem

@Composable
fun SocialMediaCard(
    card: CardItem.SocialMedia,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CardHeader(
            title = "Social Media",
            subtitle = "Connect",
        )

        Spacer(Modifier.height(20.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.9f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        )

        Spacer(Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            card.links.website?.let { url ->
                SocialMediaItem(
                    iconRes = R.drawable.website,
                    text = stringResource(R.string.website),
                    contentDescription = stringResource(R.string.website),
                    onClick = { openUrl(context, "https://$url") },
                )
            }

            card.links.facebook?.let { url ->
                SocialMediaItem(
                    iconRes = R.drawable.facebook,
                    text = stringResource(R.string.facebook),
                    contentDescription = stringResource(R.string.facebook_logo_desc),
                    onClick = { openUrl(context, "https://$url") },
                )
            }

            card.links.twitter?.let { url ->
                SocialMediaItem(
                    iconRes = R.drawable.x,
                    text = stringResource(R.string.twitter),
                    contentDescription = stringResource(R.string.twitter_logo_desc),
                    onClick = { openUrl(context, "https://$url") },
                )
            }

            card.links.instagram?.let { url ->
                SocialMediaItem(
                    iconRes = R.drawable.instagram,
                    text = stringResource(R.string.instagram),
                    contentDescription = stringResource(R.string.instagram_logo_desc),
                    onClick = { openUrl(context, "https://$url") },
                )
            }

            card.links.youtube?.let { url ->
                SocialMediaItem(
                    iconRes = R.drawable.youtube,
                    text = stringResource(R.string.youtube),
                    contentDescription = stringResource(R.string.youtube_logo_desc),
                    onClick = { openUrl(context, "https://$url") },
                )
            }
        }

        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun SocialMediaItem(
    iconRes: Int,
    text: String,
    contentDescription: String,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

private fun openUrl(
    context: android.content.Context,
    url: String,
) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}
