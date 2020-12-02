package com.example.fetchrewards.repository

import com.example.fetchrewards.database.ItemDao
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemService: ItemService,
    private val itemDao: ItemDao,
    private val itemMapper: ItemMapper
) {

    fun updateItems(): Completable {
        return itemService.getItems()
            .flatMapCompletable { items -> itemDao.insertItems(items) }
    }

    fun getItems(): Observable<List<Item>> {
        return itemDao.getItems()
            .map { roomItems -> roomItems.map { itemMapper.map(it) } }
    }
}