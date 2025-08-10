package com.example.one_badge.data.models

sealed class CardType {
    object Info: CardType()
}

data class TeamCard(
    val id: Long,
    val type: CardType,
    val title: String,
    val subtitle: String,
    val details: String = "",
)