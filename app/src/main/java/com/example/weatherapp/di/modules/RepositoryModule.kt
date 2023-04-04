package com.example.weatherapp.di.modules

import com.example.data.CitiesRepositoryImpl
import com.example.data.WeatherRepositoryImpl
import com.example.domain.CitiesRepository
import com.example.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun getWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun getCitiesRepository(impl: CitiesRepositoryImpl): CitiesRepository
}