package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.cities_dao.CitiesDao
import com.example.data.database.dao.weather_dao.DayWeatherDao
import com.example.data.database.dao.weather_dao.LocationModelDao
import com.example.data.database.dao.weather_dao.WeatherModelDao
import com.example.data.database.entities.CitiesEntity
import com.example.data.database.entities.DayWeatherEntity
import com.example.data.database.entities.LocationModelEntity
import com.example.data.database.entities.WeatherModelEntity

@Database(
    entities = [LocationModelEntity::class, WeatherModelEntity::class, DayWeatherEntity::class, CitiesEntity::class],
    version = 1
)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun getLocationModelDao(): LocationModelDao
    abstract fun getWeatherModelDao(): WeatherModelDao
    abstract fun getDayWeatherDao(): DayWeatherDao
    abstract fun getCitiesDao(): CitiesDao
}