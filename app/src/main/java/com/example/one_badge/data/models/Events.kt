package com.example.one_badge.data.models

data class EventsResponse(
    val events: List<Event>? = null,
)

data class LastEventsResponse(
    val results: List<Event>? = null,
)

data class Event(
    val idEvent: String? = null,
    val strEvent: String? = null,
    val strHomeTeam: String? = null,
    val strAwayTeam: String? = null,
    val intHomeScore: String? = null,
    val intAwayScore: String? = null,
    val dateEvent: String? = null,
    val strTime: String? = null,
    val strVenue: String? = null,
    val strLeague: String? = null,
    val strSeason: String? = null,
    val strThumb: String? = null,
    val strFanart: String? = null,
)
