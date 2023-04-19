package com.example.data.database.dao.weather_dao

import androidx.room.*
import com.example.data.database.entities.DayWeatherEntity

@Dao
interface DayWeatherDao {
    @Query("SELECT * FROM day_weather_table")
    fun getAll(): List<DayWeatherEntity>

    @Query("SELECT * FROM day_weather_table WHERE city = :needCity")
    fun getDayCityWeather(needCity: String): List<DayWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weather: List<DayWeatherEntity>)

    @Query("DELETE FROM day_weather_table")
    fun deleteAll()

    @Query("DELETE FROM day_weather_table WHERE city = :needCity")
    fun deleteDayCityWeather(needCity: String)
}