package com.example.one_badge.di

import androidx.lifecycle.ViewModel
import com.example.one_badge.data.repository.TeamRepository
import com.example.one_badge.navigation.HomeRoute
import com.example.one_badge.navigation.TeamSelectionRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartupViewModel
    @Inject
    constructor(
        private val repository: TeamRepository,
    ) : ViewModel() {
        suspend fun getStartDestination(): Any {
            val prefs = repository.getUserPreferencesOnce()
            return if (prefs?.selectedTeam != null && !prefs.isFirstLaunch) {
                HomeRoute(prefs.selectedTeam)
            } else {
                TeamSelectionRoute
            }
        }
    }
