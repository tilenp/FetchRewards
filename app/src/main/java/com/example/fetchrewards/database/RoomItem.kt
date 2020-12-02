package com.example.fetchrewards.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomItem(
    @PrimaryKey
    val id: Int,
    val listId: Int,
    val nameFormat: String,
    val nameNumber: Int?
)