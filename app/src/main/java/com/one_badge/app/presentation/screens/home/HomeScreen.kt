package com.one_badge.app.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.one_badge.app.R
import com.one_badge.app.presentation.components.AppTopBar
import com.one_badge.app.presentation.components.Carousel
import com.one_badge.app.presentation.components.SwipeCard
import com.one_badge.app.presentation.models.CardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    selectedTeam: String,
    onBackToTeamSelection: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedCard by remember { mutableStateOf<CardItem?>(null) }

    Scaffold(
        topBar = {
            AppTopBar(
                onBackClick = onBackToTeamSelection,
                teamName = selectedTeam,
            )
        },
        content = { paddingValues ->
            PullToRefreshBox(
                isRefreshing = uiState.isLoading,
                onRefresh = { viewModel.refreshContent() },
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(
                                brush =
                                    Brush.verticalGradient(
                                        colors =
                                            listOf(
                                                uiState.primaryColor,
                                                uiState.secondaryColor,
                                            ),
                                    ),
                            ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TeamBanner(bannerUrl = uiState.bannerUrl)
                    Spacer(modifier = Modifier.height(16.dp))

                    when {
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
        },
    )
}

@Composable
private fun CarouselSection(
    cards: List<CardItem>,
    onCardClick: (CardItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(420.dp),
    ) {
        Carousel(
            cards = cards,
            onCardClick = onCardClick,
        )
    }
}

@Composable
private fun SwipeCardSection(
    card: CardItem,
    onSwiped: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(24.dp),
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
            contentDescription = stringResource(R.string.team_banner_desc),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .alpha(0.8f),
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Composable
private fun TeamLogo(logoUrl: String) {
    if (logoUrl.isNotBlank()) {
        AsyncImage(
            model = logoUrl,
            contentDescription = stringResource(R.string.team_logo_desc),
            modifier =
                Modifier
                    .size(200.dp)
                    .padding(bottom = 12.dp),
        )
    } else {
        Text(
            text = stringResource(R.string.team_label),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp),
        )
    }
}

@Composable
private fun ErrorMessage(error: String) {
    Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp),
    )
}
