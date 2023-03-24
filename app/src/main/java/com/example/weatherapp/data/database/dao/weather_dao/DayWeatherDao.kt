package com.example.weatherapp.data.database.dao.weather_dao

import androidx.room.*
import com.example.weatherapp.data.database.entities.DayWeatherEntity

@Dao
interface DayWeatherDao {
    @Query("SELECT * FROM day_weather_table")
    fun getAll(): List<DayWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weather: List<DayWeatherEntity>)

    @Query("DELETE FROM day_weather_table")
    fun deleteAll()
}