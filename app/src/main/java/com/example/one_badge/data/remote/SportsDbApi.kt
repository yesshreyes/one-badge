package com.example.one_badge.data.remote

import com.example.one_badge.data.models.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsDbApi {
    @GET("api/v1/json/123/searchteams.php")
    suspend fun searchTeams(@Query("t") teamName: String): TeamResponse
}