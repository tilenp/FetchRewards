package com.example.fetchrewards.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomItem(
    @PrimaryKey
    val id: Int = 0,
    val listId: Int = 0,
    val nameFormat: String = "",
    val nameNumber: Int? = null
)