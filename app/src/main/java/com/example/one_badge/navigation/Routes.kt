package com.example.one_badge.navigation

import kotlinx.serialization.Serializable

@Serializable
object TeamSelectionRoute

@Serializable
data class HomeRoute(val teamName: String)
