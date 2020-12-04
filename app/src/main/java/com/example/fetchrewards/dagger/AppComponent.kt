package com.example.fetchrewards.dagger

import android.content.Context
import com.example.fetchrewards.dagger.module.ApiModule
import com.example.fetchrewards.dagger.module.AppModule
import com.example.fetchrewards.dagger.module.DatabaseModule
import com.example.fetchrewards.dagger.module.ServiceModule
import com.example.fetchrewards.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ApiModule::class,
    DatabaseModule::class,
    ServiceModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}