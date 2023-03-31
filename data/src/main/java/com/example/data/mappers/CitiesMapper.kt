package com.example.data.mappers

import com.example.data.database.entities.CitiesEntity
import com.example.data.models.cities_models.CityResponse
import javax.inject.Inject

class CitiesMapper @Inject constructor() {

    fun responseToEntity(response: CityResponse): CitiesEntity {
        val str = response.city.lowercase()
        return CitiesEntity(city = str[0].uppercase() + str.substring(1, str.length))
    }

    fun sort(responseList: List<CitiesEntity>): List<CitiesEntity> {
        if (responseList.size <= 1) return responseList
        val pivot = responseList[responseList.size / 2].city
        val equal = responseList.filter { it.city == pivot }
        val less = responseList.filter { it.city < pivot }
        val greater = responseList.filter { it.city > pivot }
        return sort(less) + equal + sort(greater)
    }

    fun entityToDefault(entity: CitiesEntity): String = entity.city

}