package com.example.fetchrewards.dagger.module

import com.example.fetchrewards.repository.ItemServiceImpl
import com.example.fetchrewards.network.ItemService
import dagger.Binds
import dagger.Module

@Module
interface ServiceModule {

    @Binds
    fun providesItemService(itemServiceImpl: ItemServiceImpl): ItemService
}