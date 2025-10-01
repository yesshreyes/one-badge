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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.one_badge.R

@Composable
fun SocialMediaLinks(details: String) {
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
