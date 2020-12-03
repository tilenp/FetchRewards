package com.example.fetchrewards.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(roomItems: List<RoomItem>): Completable

    @Query("SELECT * FROM RoomItem ORDER BY RoomItem.listId ASC, RoomItem.nameNumber ASC")
    fun getItems(): DataSource.Factory<Int, RoomItem>
}