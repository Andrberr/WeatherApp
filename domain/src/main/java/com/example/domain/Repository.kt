package com.example.domain

import com.example.domain.models.AddedCityInfo
import com.example.domain.models.WeatherInfo


interface Repository {
    suspend fun getWeatherInfo(cache: Boolean, city: String): WeatherInfo
    suspend fun getCities(cache: Boolean): List<String>
    suspend fun getAddedCitiesInfo(): List<AddedCityInfo>
    fun setUserCity(city: String)
    fun getUserCity(): String
}