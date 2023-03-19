package com.example.weatherapp.data.di

import android.content.Context
import com.example.weatherapp.data.di.modules.*
import com.example.weatherapp.ui.CurrentWeatherFragment
import com.example.weatherapp.ui.FutureWeatherFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, ViewModelModule::class, AppSubComponents::class, DataBaseModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun weatherComponent(): WeatherComponent.Factory
}