package com.example.one_badge.data.models

data class TableResponse(
    val table: List<TableTeam>? = null,
)

data class TableTeam(
    val intRank: String? = null,
    val strTeam: String? = null,
    val intPoints: String? = null,
    val strBadge: String? = null,
)
