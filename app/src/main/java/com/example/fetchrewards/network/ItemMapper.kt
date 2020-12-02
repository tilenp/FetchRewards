package com.example.fetchrewards.network

import com.example.fetchrewards.database.Item
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemMapper @Inject constructor() {

    fun map(remoteItem: RemoteItem): Item {
        return Item(
            id = remoteItem.id ?: 0,
            listId = remoteItem.listId ?: 0,
            name = remoteItem.name ?: ""
        )
    }
}