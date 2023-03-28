package com.example.weatherapp.data.database.dao.weather_dao

import androidx.room.*
import com.example.weatherapp.data.database.entities.DayWeatherEntity
import com.example.weatherapp.data.database.entities.WeatherModelEntity

@Dao
interface WeatherModelDao {
    @Query("SELECT * FROM current_weather_table")
    fun getAll(): WeatherModelEntity

    @Query("SELECT * FROM current_weather_table WHERE city = :needCity")
    fun getCurrentCityWeather(needCity: String): WeatherModelEntity

    @Query("SELECT tempC FROM current_weather_table WHERE city = :cityParam")
    fun getTemperatureForCity(cityParam: String): Float

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: WeatherModelEntity)

    @Query("DELETE FROM current_weather_table")
    fun delete()

    @Query("DELETE FROM current_weather_table WHERE city = :needCity")
    fun deleteCurrentCityWeather(needCity: String)
}