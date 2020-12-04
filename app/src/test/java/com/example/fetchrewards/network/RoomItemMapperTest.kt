package com.example.fetchrewards.network

import com.example.fetchrewards.NAME_SEPARATOR
import org.junit.Assert.assertEquals
import org.junit.Test

class RoomItemMapperTest {

    private val mapper = RoomItemMapper()

    @Test
    fun non_null_parameters_are_mapped_correctly() {
        // arrange
        val remoteItem = RemoteItem(1, 2, "Item 123")

        // act
        val result = mapper.map(remoteItem)

        // assert
        assertEquals(1, result.id)
        assertEquals(2, result.listId)
        assertEquals("Item $NAME_SEPARATOR", result.nameFormat)
        assertEquals(123, result.nameNumber)
    }

    @Test
    fun null_parameters_are_mapped_correctly() {
        // arrange
        val remoteItem = RemoteItem(null, null, null)

        // act
        val result = mapper.map(remoteItem)

        // assert
        assertEquals(0, result.id)
        assertEquals(0, result.listId)
        assertEquals("", result.nameFormat)
        assertEquals(null, result.nameNumber)
    }

    @Test
    fun single_digit_numbers_are_parsed_correctly() {
        // act
        val result = mapper.parseNumber("Item 1")

        // assert
        assertEquals(1, result)
    }

    @Test
    fun multi_digit_numbers_are_parsed_correctly() {
        // act
        val result = mapper.parseNumber("Item 12")

        // assert
        assertEquals(12, result)
    }

    @Test
    fun names_without_a_number_are_parsed_correctly() {
        // act
        val result = mapper.parseNumber("Item ")

        // assert
        assertEquals(null, result)
    }

    @Test
    fun names_with_a_number_are_formatted_correctly() {
        // act
        val name = "Item 12"
        val number = 12
        val result = mapper.getNameFormat(name, number)

        // assert
        assertEquals("Item $NAME_SEPARATOR", result)
    }

    @Test
    fun null_names_are_formatted_correctly() {
        // act
        val result = mapper.getNameFormat(null, null)

        // assert
        assertEquals("", result)
    }

    @Test
    fun names_without_a_number_are_formatted_correctly() {
        // act
        val noNumberName = "Item "
        val result = mapper.getNameFormat(noNumberName, null)

        // assert
        assertEquals(noNumberName, result)
    }
}