package com.example.fetchrewards.repository

import com.example.fetchrewards.database.RoomItem
import com.example.fetchrewards.network.ItemApi
import com.example.fetchrewards.network.RemoteItem
import com.example.fetchrewards.network.RoomItemMapper
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Test

class ItemServiceImplTest {

    private val itemApi: ItemApi = mock()
    private val roomRoomItemMapper: RoomItemMapper = mock()
    private lateinit var itemServiceImpl: ItemServiceImpl

    private val blankNameRemoteItem =
        RemoteItem(name = "")
    private val nullNameRemoteItemItem =
        RemoteItem(name = null)
    private val validRemoteItem =
        RemoteItem(name = "Item 1")

    fun setUp(remoteItems: List<RemoteItem>) {
        whenever(itemApi.getItems()).thenReturn(Single.just(remoteItems))
        whenever(roomRoomItemMapper.map(any())).thenReturn(RoomItem())
        itemServiceImpl = ItemServiceImpl(
            itemApi,
            roomRoomItemMapper
        )
    }

    @Test
    fun items_with_blank_names_are_filtered_out() {
        // arrange
        val blankNamesList = listOf(blankNameRemoteItem, validRemoteItem)
        setUp(blankNamesList)

        // act
        itemServiceImpl.getItems()
            .test()
            .assertComplete()
            .assertNoErrors()
            .dispose()

        // assert
        verify(roomRoomItemMapper, times(1)).map(validRemoteItem)
    }

    @Test
    fun items_with_null_names_are_filtered_out() {
        // arrange
        val nullNamesList = listOf(nullNameRemoteItemItem, validRemoteItem)
        setUp(nullNamesList)

        // act
        itemServiceImpl.getItems()
            .test()
            .assertComplete()
            .assertNoErrors()
            .dispose()

        // assert
        verify(roomRoomItemMapper, times(1)).map(validRemoteItem)
    }
}