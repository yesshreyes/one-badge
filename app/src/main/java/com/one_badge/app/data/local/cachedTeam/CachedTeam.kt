package com.one_badge.app.data.local.cachedTeam

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_teams")
data class CachedTeam(
    @PrimaryKey val teamName: String,
    val badgeUrl: String,
    val cachedAt: Long = System.currentTimeMillis(),
)
