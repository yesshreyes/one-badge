package com.example.one_badge.data.remote

import com.example.one_badge.BuildConfig
import com.example.one_badge.data.models.EventsResponse
import com.example.one_badge.data.models.LastEventsResponse
import com.example.one_badge.data.models.PlayersResponse
import com.example.one_badge.data.models.TableResponse
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

    @GET("api/v1/json/{apiKey}/eventsnext.php")
    suspend fun getNextMatches(
        @Path("apiKey") apiKey: String = BuildConfig.SPORTS_DB_API_KEY,
        @Query("id") teamId: String,
    ): EventsResponse

    @GET("api/v1/json/{apiKey}/eventslast.php")
    suspend fun getLastMatches(
        @Path("apiKey") apiKey: String = BuildConfig.SPORTS_DB_API_KEY,
        @Query("id") teamId: String,
    ): LastEventsResponse

    @GET("api/v1/json/{apiKey}/lookup_all_players.php")
    suspend fun getPlayers(
        @Path("apiKey") apiKey: String = BuildConfig.SPORTS_DB_API_KEY,
        @Query("id") teamId: String,
    ): PlayersResponse

    @GET("api/v1/json/{apiKey}/lookuptable.php")
    suspend fun getLeagueTable(
        @Path("apiKey") apiKey: String = BuildConfig.SPORTS_DB_API_KEY,
        @Query("l") leagueId: String,
        @Query("s") season: String,
    ): TableResponse
}
