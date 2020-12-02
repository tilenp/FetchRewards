package com.example.fetchrewards.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey
    val id: Int,
    val listId: Int,
    val name: String
)