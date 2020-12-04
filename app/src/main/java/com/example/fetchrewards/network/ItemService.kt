package com.example.fetchrewards.network

import com.example.fetchrewards.database.RoomItem
import io.reactivex.Single

interface ItemService {
    fun getItems(): Single<List<RoomItem>>
}