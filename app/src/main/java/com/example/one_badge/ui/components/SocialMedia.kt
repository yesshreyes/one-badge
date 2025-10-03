package com.example.one_badge.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@Composable
fun SocialMediaLinks(details: String) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (details.contains(stringResource(R.string.facebook))) {
            val facebookUrl = extractUrl(details, stringResource(R.string.facebook))
            ClickableSocialItem(
                iconRes = R.drawable.facebook,
                text = stringResource(R.string.facebook),
                contentDescription = stringResource(R.string.facebook_logo_desc),
                onClick = { openUrl(context, "https://$facebookUrl") },
            )
        }
        if (details.contains(stringResource(R.string.twitter))) {
            val twitterUrl = extractUrl(details, stringResource(R.string.twitter))
            ClickableSocialItem(
                iconRes = R.drawable.x,
                text = stringResource(R.string.twitter),
                contentDescription = stringResource(R.string.twitter_logo_desc),
                onClick = { openUrl(context, "https://$twitterUrl") },
            )
        }
        if (details.contains(stringResource(R.string.instagram))) {
            val instagramUrl = extractUrl(details, stringResource(R.string.instagram))
            ClickableSocialItem(
                iconRes = R.drawable.instagram,
                text = stringResource(R.string.instagram),
                contentDescription = stringResource(R.string.instagram_logo_desc),
                onClick = { openUrl(context, "https://$instagramUrl") },
            )
        }
        if (details.contains(stringResource(R.string.youtube))) {
            val youtubeUrl = extractUrl(details, stringResource(R.string.youtube))
            ClickableSocialItem(
                iconRes = R.drawable.youtube,
                text = stringResource(R.string.youtube),
                contentDescription = stringResource(R.string.youtube_logo_desc),
                onClick = { openUrl(context, "https://$youtubeUrl") },
            )
        }
    }
}

@Composable
private fun ClickableSocialItem(
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

private fun extractUrl(
    details: String,
    platform: String,
): String {
    val lines = details.split("\n")
    val platformLine = lines.find { it.contains(platform) }
    return platformLine?.substringAfter("$platform\n") ?: ""
}

private fun openUrl(
    context: android.content.Context,
    url: String,
) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}
