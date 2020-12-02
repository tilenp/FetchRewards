package com.example.fetchrewards.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fetchrewards.DATABASE_NAME

@Database(entities = [RoomItem::class], version = 1, exportSchema = true)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun getItemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): ItemDatabase {
            return Room.databaseBuilder(context, ItemDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}