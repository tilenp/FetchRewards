package com.example.fetchrewards.dagger.module

import com.example.fetchrewards.repository.service.ItemServiceImpl
import com.example.fetchrewards.repository.service.ItemService
import dagger.Binds
import dagger.Module

@Module
interface ServiceModule {

    @Binds
    fun providesItemService(itemServiceImpl: ItemServiceImpl): ItemService
}