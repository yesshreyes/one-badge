package com.example.one_badge.data.remote

import com.example.one_badge.BuildConfig
import com.example.one_badge.data.models.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SportsDbApi {
    @GET("api/v1/json/{apiKey}/searchteams.php")
    suspend fun searchTeams(
        @Path("apiKey") apiKey: String = BuildConfig.SPORTS_DB_API_KEY,
        @Query("t") teamName: String,
    ): TeamResponse
}
