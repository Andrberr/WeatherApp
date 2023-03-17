package com.example.weatherapp.data.database.dao

import androidx.room.*
import com.example.weatherapp.data.database.entities.DayWeatherEntity
import com.example.weatherapp.data.database.entities.WeatherModelEntity

@Dao
interface DayWeatherDao {
    @Query("SELECT * FROM day_weather_table")
    fun getAll(): List<DayWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weather: List<DayWeatherEntity>)

    @Delete
    fun deleteAll(weather: List<DayWeatherEntity>)
}