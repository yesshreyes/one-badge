package com.example.one_badge.data.repository

import com.example.one_badge.data.models.TeamResponse
import com.example.one_badge.data.remote.SportsDbApi

class TeamRepository(private val api: SportsDbApi) {
    suspend fun getTeamByName(name: String): TeamResponse = api.searchTeams(name)
}