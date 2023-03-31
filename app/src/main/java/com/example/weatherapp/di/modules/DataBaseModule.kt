package com.example.weatherapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.data.database.WeatherDataBase
import com.example.data.database.dao.cities_dao.CitiesDao
import com.example.data.database.dao.weather_dao.DayWeatherDao
import com.example.data.database.dao.weather_dao.LocationModelDao
import com.example.data.database.dao.weather_dao.WeatherModelDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(context: Context) =
        Room.databaseBuilder(context, WeatherDataBase::class.java, "weather_database").build()

    @Provides
    @Singleton
    fun provideDayWeatherDao(db: WeatherDataBase): DayWeatherDao = db.getDayWeatherDao()

    @Provides
    @Singleton
    fun provideLocationModelDao(db: WeatherDataBase): LocationModelDao = db.getLocationModelDao()

    @Provides
    @Singleton
    fun provideWeatherModelDao(db: WeatherDataBase): WeatherModelDao = db.getWeatherModelDao()

    @Provides
    @Singleton
    fun provideCitiesDao(db: WeatherDataBase): CitiesDao = db.getCitiesDao()
}