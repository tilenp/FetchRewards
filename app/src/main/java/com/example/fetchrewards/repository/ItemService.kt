package com.example.fetchrewards.repository

import com.example.fetchrewards.database.Item
import io.reactivex.Single

interface ItemService {
    fun getItems(): Single<List<Item>>
}