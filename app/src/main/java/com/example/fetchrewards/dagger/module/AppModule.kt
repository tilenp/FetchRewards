package com.example.fetchrewards.dagger.module

import com.example.fetchrewards.utils.RuntimeSchedulerProvider
import com.example.fetchrewards.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesSchedulerProvider(): SchedulerProvider {
        return RuntimeSchedulerProvider()
    }
}