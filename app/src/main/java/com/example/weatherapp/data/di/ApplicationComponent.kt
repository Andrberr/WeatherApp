package com.example.weatherapp.data.di

import android.content.Context
import com.example.weatherapp.data.di.modules.AppSubComponents
import com.example.weatherapp.ui.CurrentWeatherFragment
import com.example.weatherapp.data.di.modules.NetworkModule
import com.example.weatherapp.data.di.modules.RepositoryModule
import com.example.weatherapp.data.di.modules.ViewModelModule
import com.example.weatherapp.ui.FutureWeatherFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, ViewModelModule::class, AppSubComponents::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun weatherComponent(): WeatherComponent.Factory
}