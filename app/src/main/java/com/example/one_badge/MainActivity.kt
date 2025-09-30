package com.example.one_badge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.one_badge.data.remote.RetrofitInstance
import com.example.one_badge.data.repository.TeamRepository
import com.example.one_badge.ui.screens.TeamSelectionScreen
import com.example.one_badge.ui.screens.home.HomeScreen
import com.example.one_badge.ui.screens.home.HomeViewModel
import com.example.one_badge.ui.screens.team.TeamSelectionViewModel
import com.example.one_badge.ui.theme.OnebadgeTheme

class MainActivity : ComponentActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var teamSelectionViewModel: TeamSelectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = TeamRepository(RetrofitInstance.api)
        homeViewModel = HomeViewModel(repository)
        teamSelectionViewModel = TeamSelectionViewModel(repository)

        setContent {
            OnebadgeTheme {
                MainContent()
            }
        }
    }

    @Composable
    private fun MainContent() {
        var selectedTeam by remember { mutableStateOf<String?>(null) }

        if (selectedTeam == null) {
            TeamSelectionScreen(
                viewModel = teamSelectionViewModel,
                onTeamSelected = { teamName ->
                    selectedTeam = teamName
                }
            )
        } else {
            HomeScreen(
                viewModel = homeViewModel,
                selectedTeam = selectedTeam!!,
                onBackToTeamSelection = { selectedTeam = null }
            )
        }
    }
}
