package com.one_badge.app.di

import androidx.lifecycle.ViewModel
import com.one_badge.app.data.repository.TeamRepository
import com.one_badge.app.navigation.HomeRoute
import com.one_badge.app.navigation.TeamSelectionRoute
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
