package com.example.one_badge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.one_badge.data.local.AppDatabase
import com.example.one_badge.data.remote.RetrofitInstance
import com.example.one_badge.data.repository.TeamRepository
import com.example.one_badge.ui.screens.home.HomeScreen
import com.example.one_badge.ui.screens.home.HomeViewModel
import com.example.one_badge.ui.screens.team.TeamSelectionScreen
import com.example.one_badge.ui.screens.team.TeamSelectionViewModel
import com.example.one_badge.ui.theme.OnebadgeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var teamSelectionViewModel: TeamSelectionViewModel
    private lateinit var repository: TeamRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val userPreferencesDao = database.userPreferencesDao()
        val cachedTeamDao = database.cachedTeamDao()

        repository = TeamRepository(RetrofitInstance.api, userPreferencesDao, cachedTeamDao)
        homeViewModel = HomeViewModel(repository)
        teamSelectionViewModel = TeamSelectionViewModel(repository)

        setContent {
            OnebadgeTheme {
                mainContent()
            }
        }
    }

    @Composable
    private fun mainContent() {
        val teamSelectionUiState by teamSelectionViewModel.uiState.collectAsState()
        var currentScreen by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            lifecycleScope.launch {
                val isFirstLaunch = repository.isFirstLaunch()
                if (!isFirstLaunch) {
                    val prefs = repository.getUserPreferencesOnce()
                    currentScreen = prefs?.selectedTeam
                }
            }
        }

        LaunchedEffect(teamSelectionUiState.selectedTeam) {
            currentScreen = teamSelectionUiState.selectedTeam
        }

        when {
            currentScreen == null -> {
                TeamSelectionScreen(
                    viewModel = teamSelectionViewModel,
                    onTeamSelected = { teamName ->
                        teamSelectionViewModel.selectTeam(teamName)
                    },
                )
            }
            else -> {
                HomeScreen(
                    viewModel = homeViewModel,
                    selectedTeam = currentScreen!!,
                    onBackToTeamSelection = {
                        teamSelectionViewModel.clearTeamSelection()
                    },
                )
            }
        }
    }
}
