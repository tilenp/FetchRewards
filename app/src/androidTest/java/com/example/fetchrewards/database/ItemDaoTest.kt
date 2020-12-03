package com.example.fetchrewards.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var database: ItemDatabase
    private lateinit var itemDao: ItemDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun setUp(roomItems: List<RoomItem>) {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            database = Room.inMemoryDatabaseBuilder(context, ItemDatabase::class.java).build()

            itemDao = database.getItemDao()
            itemDao.insertItems(roomItems).blockingAwait()
        }
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun items_are_inserted_correctly() {
        //arrange
        setUp(emptyList())

        // assert
        itemDao.insertItems(emptyList())
            .test()
            .assertComplete()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun get_item_count_works_correctly() {
        //arrange
        setUp(listOf(RoomItem()))

        // assert
        itemDao.getItemCount()
            .test()
            .assertValue(1)
            .assertNoErrors()
            .dispose()
    }
}