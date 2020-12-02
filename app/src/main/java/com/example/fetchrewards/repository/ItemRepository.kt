package com.example.fetchrewards.repository

import com.example.fetchrewards.database.Item
import com.example.fetchrewards.database.ItemDao
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemService: ItemService,
    private val itemDao: ItemDao
) {
    fun updateItems(): Completable {
        return itemService.getItems()
            .flatMapCompletable { items -> itemDao.insertItems(items) }
    }

    fun getItems(): Observable<List<Item>> {
        return itemDao.getItems()
    }
}