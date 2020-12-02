package com.example.fetchrewards.dagger

import com.example.fetchrewards.network.ItemServiceImpl
import com.example.fetchrewards.repository.ItemService
import dagger.Binds
import dagger.Module

@Module
interface ServiceModule {

    @Binds
    fun providesItemService(itemServiceImpl: ItemServiceImpl): ItemService
}