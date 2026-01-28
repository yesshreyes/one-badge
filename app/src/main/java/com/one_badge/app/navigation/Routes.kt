package com.one_badge.app.navigation

import kotlinx.serialization.Serializable

@Serializable
object TeamSelectionRoute

@Serializable
data class HomeRoute(val teamName: String)
