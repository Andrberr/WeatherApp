package com.example.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.database.dao.DayWeatherDao
import com.example.weatherapp.data.database.dao.LocationModelDao
import com.example.weatherapp.data.database.dao.WeatherModelDao
import com.example.weatherapp.data.database.entities.DayWeatherEntity
import com.example.weatherapp.data.database.entities.LocationModelEntity
import com.example.weatherapp.data.database.entities.WeatherModelEntity

@Database(entities = [LocationModelEntity::class, WeatherModelEntity::class, DayWeatherEntity::class], version=1)
abstract class WeatherDataBase: RoomDatabase() {
    abstract fun getLocationModelDao(): LocationModelDao
    abstract fun getWeatherModelDao(): WeatherModelDao
    abstract fun getDayWeatherDao(): DayWeatherDao
}