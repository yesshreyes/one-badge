package com.one_badge.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.one_badge.app.di.StartupViewModel
import com.one_badge.app.navigation.NavGraph

@Composable
fun MainContent() {
    val startupViewModel: StartupViewModel = hiltViewModel()
    var startDestination by remember { mutableStateOf<Any?>(null) }

    LaunchedEffect(Unit) {
        startDestination = startupViewModel.getStartDestination()
    }

    startDestination?.let {
        NavGraph(startDestination = it)
    }
}
