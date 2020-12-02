package com.example.fetchrewards.repository

import com.example.fetchrewards.NAME_SEPARATOR
import com.example.fetchrewards.database.RoomItem
import org.junit.Assert.assertEquals
import org.junit.Test

class ItemMapperTest {

    private val mapper = ItemMapper()

    @Test
    fun parameters_are_mapped_correctly() {
        // arrange
        val id = 1
        val listId = 2
        val nameFormat = "Item $NAME_SEPARATOR"
        val nameNumber = 123
        val name = "Item 123"
        val roomItem = RoomItem(id, listId, nameFormat, nameNumber)

        // act
        val result = mapper.map(roomItem)

        // assert
        assertEquals(id, result.id)
        assertEquals(listId, result.listId)
        assertEquals(name, result.name)
    }
}