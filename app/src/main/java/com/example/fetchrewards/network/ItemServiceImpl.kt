package com.example.fetchrewards.network

import com.example.fetchrewards.database.RoomItem
import com.example.fetchrewards.repository.ItemService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemServiceImpl @Inject constructor(
    private val itemApi: ItemApi,
    private val itemMapper: ItemMapper
): ItemService {

    override fun getItems(): Single<List<RoomItem>> {
        return itemApi.getItems()
            .map { remoteItems -> remoteItems.filter { !it.name.isNullOrEmpty() } }
            .map { remoteItems -> remoteItems.map { itemMapper.map(it) } }
    }
}