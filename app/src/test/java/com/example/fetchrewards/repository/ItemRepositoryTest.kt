package com.example.fetchrewards.repository

import com.example.fetchrewards.database.ItemDao
import com.example.fetchrewards.database.RoomItem
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

class ItemRepositoryTest {

    private val itemService: ItemService = mock()
    private val itemDao: ItemDao = mock()
    private val itemMapper: ItemMapper = mock()
    private lateinit var itemRepository: ItemRepository

    fun setUp(roomItems: List<RoomItem>) {
        whenever(itemService.getItems()).thenReturn(Single.just(roomItems))
        whenever(itemDao.insertItems(any())).thenReturn(Completable.complete())
        itemRepository = ItemRepository(itemService, itemDao, itemMapper)
    }

    @Test
    fun when_items_are_loaded_database_is_updated() {
        // arrange
        setUp(emptyList())

        // act
        itemRepository.updateItems()
            .test()
            .assertComplete()
            .assertNoErrors()
            .dispose()

        // assert
        verify(itemDao, times(1)).insertItems(emptyList())
    }
}