package com.example.one_badge.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreferences(
    @PrimaryKey val id: Int = 1,
    val selectedTeam: String? = null,
    val isFirstLaunch: Boolean = true,
    val lastSelectedAt: Long = System.currentTimeMillis(),
)
