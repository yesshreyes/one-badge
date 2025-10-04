package com.example.one_badge.data.local.cachedTeam

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_teams")
data class CachedTeam(
    @PrimaryKey val teamName: String,
    val badgeUrl: String,
    val cachedAt: Long = System.currentTimeMillis(),
)
