package com.example.weatherapp.data.database.dao

import androidx.room.*
import com.example.weatherapp.data.database.entities.WeatherModelEntity

@Dao
interface WeatherModelDao {
    @Query("SELECT * FROM current_weather_table")
    fun getAll(): WeatherModelEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: WeatherModelEntity)

    @Delete
    fun delete(weather: WeatherModelEntity)
}