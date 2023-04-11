package com.example.domain.repositories

import com.example.domain.models.AddedCityInfo

interface CitiesRepository {
    suspend fun getCities(cache: Boolean): List<String>
    suspend fun getAddedCitiesInfo(): List<AddedCityInfo>
    fun setUserCity(city: String)
    fun getUserCity(): String
}