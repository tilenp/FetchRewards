package com.example.fetchrewards.dagger

import android.content.Context
import com.example.fetchrewards.database.ItemDao
import com.example.fetchrewards.database.ItemDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesItemDatabase(context: Context): ItemDatabase {
        return ItemDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun providesItemDao(database: ItemDatabase): ItemDao {
        return database.getItemDao()
    }
}