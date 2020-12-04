package com.example.fetchrewards.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.example.fetchrewards.BASE_URL
import com.example.fetchrewards.dagger.ItemViewModelFactory
import com.example.fetchrewards.network.ItemApi
import com.example.fetchrewards.repository.ItemRepository
import com.example.fetchrewards.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun providesItemViewModelFactory(itemRepository: ItemRepository, schedulerProvider: SchedulerProvider): ViewModelProvider.Factory {
        return ItemViewModelFactory(itemRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun providesRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesItemApi(
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): ItemApi {

        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(ItemApi::class.java)
    }
}