package com.example.weatherapp.domain

import com.example.weatherapp.domain.models.WeatherInfo

interface Repository {
    suspend fun getWeatherInfo(cache: Boolean): WeatherInfo
}