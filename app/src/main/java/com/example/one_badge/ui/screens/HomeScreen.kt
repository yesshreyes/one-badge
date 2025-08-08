package com.example.one_badge.ui.screens

import CarouselWithIndicators
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.one_badge.data.models.BadgeCard
import com.example.one_badge.data.models.sampleCards
import com.example.one_badge.ui.components.SwipeCard
import com.example.one_badge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val cards = remember { sampleCards }
    var selectedCard by remember { mutableStateOf<BadgeCard?>(null) }
    val teamLogoUrl by viewModel.logoUrl.collectAsState()

    // Fetch on launch
    LaunchedEffect(Unit) {
        viewModel.fetchTeamLogo("Arsenal")
    }

    Scaffold(
        topBar = {
            TopAppBar( title = {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_onebadge_logo),
                    contentDescription = "One Badge Logo",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "One Badge",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
            colors = TopAppBarDefaults.topAppBarColors()
            )},
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(34.dp))

                if (teamLogoUrl != null) {
                    AsyncImage(
                        model = teamLogoUrl,
                        contentDescription = "Team Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 12.dp)
                    )
                } else {
                    Text(
                        text = "Arsenal",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                Spacer(Modifier.height(10.dp))

                if (selectedCard == null) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        CarouselWithIndicators(
                            cards = cards,
                            onCardClick = { selectedCard = it }
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_onebadge_logo), // replace with your crest drawable resource
                        contentDescription = "Crest Logo",
                        modifier = Modifier
                            .size(220.dp)
                            .padding(top = 16.dp, bottom = 24.dp)
                            .alpha(0.5f)
                    )

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
            }
        }
    )
}
