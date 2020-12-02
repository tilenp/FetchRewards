package com.example.fetchrewards.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Item>): Completable

    @Query("SELECT * FROM Item ORDER BY Item.listId ASC, Item.nameNumber ASC")
    fun getItems(): Observable<List<Item>>
}