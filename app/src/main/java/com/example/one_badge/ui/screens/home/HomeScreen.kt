package com.example.one_badge.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.one_badge.data.models.TeamCard
import com.example.one_badge.ui.components.Carousel
import com.example.one_badge.ui.components.SwipeCard
import com.example.one_badge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    selectedTeam: String,
    onBackToTeamSelection: (() -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedCard by remember { mutableStateOf<TeamCard?>(null) }

    LaunchedEffect(selectedTeam) {
        viewModel.fetchTeamData(selectedTeam)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                onBackClick = onBackToTeamSelection,
                teamName = selectedTeam
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
                TeamBanner(bannerUrl = uiState.bannerUrl)
                Spacer(modifier = Modifier.height(16.dp))

                when {
                    uiState.isLoading -> {
                        Box(modifier = Modifier.weight(1f)) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    uiState.error != null -> {
                        ErrorMessage(error = uiState.error!!)
                    }
                    selectedCard != null -> {
                        SwipeCardSection(
                            card = selectedCard!!,
                            onSwiped = { selectedCard = null }
                        )
                    }
                    else -> {
                        CarouselSection(
                            cards = uiState.cards,
                            onCardClick = { selectedCard = it }
                        )
                        TeamLogo(logoUrl = uiState.logoUrl)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    onBackClick: (() -> Unit)? = null,
    teamName: String
) {
    var showMenu by remember { mutableStateOf(false) }

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
                Text(
                    text = "One",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Badge",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = teamName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            onBackClick?.let { callback ->
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Change Team") },
                            onClick = {
                                showMenu = false
                                callback()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}


@Composable
private fun TeamLogo(logoUrl: String) {
    if (logoUrl.isNotBlank()) {
        AsyncImage(
            model = logoUrl,
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
}

@Composable
private fun CarouselSection(
    cards: List<TeamCard>,
    onCardClick: (TeamCard) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        Carousel(
            cards = cards,
            onCardClick = onCardClick
        )
    }
}

@Composable
private fun TeamBanner(bannerUrl: String) {
    if (bannerUrl.isNotBlank()) {
        AsyncImage(
            model = bannerUrl,
            contentDescription = "Team Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .alpha(0.8f),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun SwipeCardSection(
    card: TeamCard,
    onSwiped: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(24.dp)
    ) {
        SwipeCard(
            card = card,
            onSwiped = { _ -> onSwiped() },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ErrorMessage(error: String) {
    Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)
    )
}
