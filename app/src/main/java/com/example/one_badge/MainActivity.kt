package com.example.one_badge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.one_badge.data.remote.RetrofitInstance
import com.example.one_badge.data.repository.TeamRepository
import com.example.one_badge.ui.screens.HomeScreen
import com.example.one_badge.ui.screens.HomeViewModel
import com.example.one_badge.ui.theme.OnebadgeTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = TeamRepository(RetrofitInstance.api)
        viewModel = HomeViewModel(repository)

        setContent {
            OnebadgeTheme {
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}
