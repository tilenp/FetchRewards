package com.example.fetchrewards.repository.service

import com.example.fetchrewards.database.RoomItem
import com.example.fetchrewards.network.ItemApi
import com.example.fetchrewards.network.RoomItemMapper
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemServiceImpl @Inject constructor(
    private val itemApi: ItemApi,
    private val roomItemMapper: RoomItemMapper
): ItemService {

    override fun getItems(): Single<List<RoomItem>> {
        return itemApi.getItems()
            .map { remoteItems -> remoteItems.filter { !it.name.isNullOrEmpty() } }
            .map { remoteItems -> remoteItems.map { roomItemMapper.map(it) } }
    }
}