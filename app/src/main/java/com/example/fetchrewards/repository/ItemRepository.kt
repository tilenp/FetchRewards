package com.example.fetchrewards.repository

import androidx.paging.DataSource
import com.example.fetchrewards.database.ItemDao
import com.example.fetchrewards.repository.service.ItemService
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

    fun getItemCount(): Observable<Int> {
        return itemDao.getItemCount()
    }

    fun getItems(): DataSource.Factory<Int, Item>{
        return itemDao.getItems().map { itemMapper.map(it) }
    }
}