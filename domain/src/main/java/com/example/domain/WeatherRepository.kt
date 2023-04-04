package com.example.domain

import com.example.domain.models.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherInfo(cache: Boolean, city: String): WeatherInfo

    suspend fun deleteCityFromDatabase(city: String)
}