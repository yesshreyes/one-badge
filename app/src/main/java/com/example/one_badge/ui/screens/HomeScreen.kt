package com.example.one_badge.ui.screens

import com.example.one_badge.ui.components.Carousel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.one_badge.data.models.TeamCard
import com.example.one_badge.data.models.CardType
import com.example.one_badge.ui.components.SwipeCard
import com.example.one_badge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val teamLogoUrl by viewModel.logoUrl.collectAsState()
    val teamBadgeUrl by viewModel.badgeUrl.collectAsState()

    val teamInfo by viewModel.teamInfo.collectAsState()
    val teamDescription by viewModel.teamDescription.collectAsState()
    val teamEquipment by viewModel.teamEquipment.collectAsState()
    val socialMedia by viewModel.socialMedia.collectAsState()

    var selectedCard by remember { mutableStateOf<TeamCard?>(null) }

    var showTeamDialog by remember { mutableStateOf(false) }
    val teams = listOf("Real Madrid", "Barcelona", "Arsenal", "Chelsea")

    LaunchedEffect(Unit) {
        viewModel.fetchTeamData("Real Madrid")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_onebadge_logo),
                            contentDescription = "One Badge Logo",
                            modifier = Modifier.size(40.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Row {
                            Text(
                                text = "One",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Badge",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                                )
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!teamLogoUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = teamLogoUrl,
                        contentDescription = "Team Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 12.dp)
                    )
                } else {
                    Text(
                        text = "Team",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                val cards = mutableListOf<TeamCard>()

                teamInfo?.let {
                    cards.add(
                        TeamCard(
                            id = 1L,
                            type = CardType.Info,
                            title = "Team Info",
                            subtitle = it.teamName,
                            details = """
                                Formed: ${it.formedYear}
                                Stadium: ${it.stadium}
                                Location: ${it.location}
                                Capacity: ${it.stadiumCapacity}
                            """.trimIndent()
                        )
                    )
                }

                teamDescription?.let {
                    cards.add(
                        TeamCard(
                            id = 2L,
                            type = CardType.Info,
                            title = "Description",
                            subtitle = teamInfo?.teamName ?: "Team",
                            details = it
                        )
                    )
                }

                teamEquipment?.let {
                    cards.add(
                        TeamCard(
                            id = 3L,
                            type = CardType.Info,
                            title = "Equipment",
                            subtitle = teamInfo?.teamName ?: "Team",
                            details = it.ifBlank { "No equipment info" }
                        )
                    )
                }

                socialMedia?.let { it ->
                    cards.add(
                        TeamCard(
                            id = 4L,
                            type = CardType.Info,
                            title = "Social Media",
                            subtitle = teamInfo?.teamName ?: "Team",
                            details = listOfNotNull(
                                if (it.facebook.isNotBlank()) "Facebook: ${it.facebook}" else null,
                                if (it.twitter.isNotBlank()) "Twitter: ${it.twitter}" else null,
                                if (it.instagram.isNotBlank()) "Instagram: ${it.instagram}" else null,
                                if (it.youtube.isNotBlank()) "YouTube: ${it.youtube}" else null
                            ).takeIf { it.isNotEmpty() }?.joinToString("\n") ?: "No social links"
                        )
                    )
                }

                if (selectedCard == null) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Carousel(
                            cards = cards,
                            onCardClick = { selectedCard = it }
                        )
                    }

                    if (!teamBadgeUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = teamBadgeUrl,
                            contentDescription = "Team Crest",
                            modifier = Modifier
                                .size(150.dp)
                                .padding(bottom = 24.dp)
                                .alpha(0.5f)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .align(Alignment.CenterHorizontally)
                            .padding(24.dp)
                    ) {
                        SwipeCard(
                            card = selectedCard!!,
                            onSwiped = { _ -> selectedCard = null },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showTeamDialog = true }
                ) {
                    Text("Select Team")
                }
            }
            if (showTeamDialog) {
                AlertDialog(
                    onDismissRequest = { showTeamDialog = false },
                    title = { Text("Select a Team") },
                    text = {
                        Column {
                            teams.forEach { team ->
                                TextButton(
                                    onClick = {
                                        viewModel.fetchTeamData(team)
                                        showTeamDialog = false
                                    }
                                ) {
                                    Text(team)
                                }
                            }
                        }
                    },
                    confirmButton = {},
                    dismissButton = {
                        TextButton(
                            onClick = { showTeamDialog = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    )
}
