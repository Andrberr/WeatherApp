package com.example.weatherapp.data.di.modules

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.database.WeatherDataBase
import com.example.weatherapp.data.database.dao.DayWeatherDao
import com.example.weatherapp.data.database.dao.LocationModelDao
import com.example.weatherapp.data.database.dao.WeatherModelDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(context: Context) =
        Room.databaseBuilder(context, WeatherDataBase::class.java, "weather_database")

    @Provides
    @Singleton
    fun provideDayWeatherDao(db: WeatherDataBase): DayWeatherDao = db.getDayWeatherDao()

    @Provides
    @Singleton
    fun provideLocationModelDao(db: WeatherDataBase): LocationModelDao = db.getLocationModelDao()

    @Provides
    @Singleton
    fun provideWeatherModelDao(db: WeatherDataBase): WeatherModelDao = db.getWeatherModelDao()
}