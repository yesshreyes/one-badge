package com.one_badge.app.data.models

data class PlayersResponse(
    val player: List<Player>? = null,
)

data class Player(
    val idPlayer: String? = null,
    val strPlayer: String? = null,
    val strPosition: String? = null,
    val strNumber: String? = null,
    val strThumb: String? = null,
)
