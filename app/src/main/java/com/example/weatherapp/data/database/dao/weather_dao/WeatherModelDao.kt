package com.example.weatherapp.data.database.dao.weather_dao

import androidx.room.*
import com.example.weatherapp.data.database.entities.WeatherModelEntity

@Dao
interface WeatherModelDao {
    @Query("SELECT * FROM current_weather_table")
    fun getAll(): WeatherModelEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: WeatherModelEntity)

    @Query("DELETE FROM current_weather_table")
    fun delete()
}