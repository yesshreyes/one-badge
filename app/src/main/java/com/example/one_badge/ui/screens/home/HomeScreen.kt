package com.example.one_badge.ui.screens.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.one_badge.data.models.TeamCard
import com.example.one_badge.ui.components.Carousel
import com.example.one_badge.ui.components.SwipeCard
import com.example.one_badge.ui.components.AppTopBar

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
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                uiState.primaryColor,
                                uiState.secondaryColor
                            )
                        )
                    )
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TeamBanner(bannerUrl = uiState.bannerUrl)
                Spacer(modifier = Modifier.height(16.dp))

                when {
                    uiState.isLoading -> {
                        Box(modifier = Modifier.weight(1f)) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.White
                            )
                        }
                    }
                    uiState.error != null -> {
                        ErrorMessage(error = uiState.error!!)
                    }
                    selectedCard != null -> {
                        SwipeCardSection(
                            card = selectedCard!!,
                            onSwiped = { selectedCard = null },
                        )
                    }
                    else -> {
                        CarouselSection(
                            cards = uiState.cards,
                            onCardClick = { selectedCard = it },
                        )
                        TeamLogo(logoUrl = uiState.logoUrl)
                    }
                }
            }
        }
    )
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
            onCardClick = onCardClick,
        )
    }
}

@Composable
private fun SwipeCardSection(
    card: TeamCard,
    onSwiped: () -> Unit,
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
            modifier = Modifier.fillMaxSize(),
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
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
private fun TeamLogo(logoUrl: String) {
    if (logoUrl.isNotBlank()) {
        AsyncImage(
            model = logoUrl,
            contentDescription = "Team Logo",
            modifier = Modifier
                .size(200.dp)
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
private fun ErrorMessage(error: String) {
    Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)
    )
}
