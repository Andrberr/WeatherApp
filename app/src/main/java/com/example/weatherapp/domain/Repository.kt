package com.example.weatherapp.domain

import com.example.weatherapp.domain.models.AddedCityInfo
import com.example.weatherapp.domain.models.WeatherInfo


interface Repository {
    suspend fun getWeatherInfo(cache: Boolean, city: String): WeatherInfo
    suspend fun getCities(cache: Boolean): List<String>
    suspend fun getAddedCitiesInfo(): List<AddedCityInfo>
}