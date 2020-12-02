package com.example.fetchrewards.repository

import com.example.fetchrewards.database.RoomItem
import io.reactivex.Single

interface ItemService {
    fun getItems(): Single<List<RoomItem>>
}