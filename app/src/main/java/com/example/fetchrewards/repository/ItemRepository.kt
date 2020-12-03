package com.example.fetchrewards.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.fetchrewards.database.ItemDao
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
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

    fun getI(): Flowable<PagedList<Item>> {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(40)
            .setPageSize(20)
            .setPrefetchDistance(10)
            .build()
        return RxPagedListBuilder(itemDao.getI().map { itemMapper.map(it) }, config)
            .buildFlowable(BackpressureStrategy.DROP)
    }
}