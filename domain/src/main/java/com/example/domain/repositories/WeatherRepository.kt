package com.example.domain.repositories

import com.example.domain.models.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherInfo(cache: Boolean, city: String, coordinates: String): WeatherInfo?
    suspend fun deleteCityFromDatabase(city: String)
    fun getUserCity(): String
}