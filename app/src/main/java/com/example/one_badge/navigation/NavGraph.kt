package com.example.one_badge.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.one_badge.presentation.screens.home.HomeScreen
import com.example.one_badge.presentation.screens.home.HomeViewModel
import com.example.one_badge.presentation.screens.team.TeamSelectionScreen
import com.example.one_badge.presentation.screens.team.TeamSelectionViewModel

@Composable
fun NavGraph(
    startDestination: Any,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination) {
        composable<TeamSelectionRoute> {
            val viewModel: TeamSelectionViewModel = hiltViewModel()

            TeamSelectionScreen(
                viewModel = viewModel,
                onTeamSelected = { team ->
                    viewModel.selectTeam(team)
                    navController.navigate(HomeRoute(team)) {
                        popUpTo<TeamSelectionRoute> { inclusive = true }
                    }
                    viewModel.clearSelectedTeamState()
                },
            )
        }

        composable<HomeRoute> { entry ->
            val route = entry.toRoute<HomeRoute>()
            val viewModel: HomeViewModel = hiltViewModel()

            HomeScreen(
                viewModel = viewModel,
                selectedTeam = route.teamName,
                onBackToTeamSelection = {
                    navController.navigate(TeamSelectionRoute) {
                        popUpTo<HomeRoute> { inclusive = true }
                    }
                },
            )
        }
    }
}
