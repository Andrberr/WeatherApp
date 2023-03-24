package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.database.entities.CitiesEntity
import com.example.weatherapp.data.models.cities_models.CityResponse
import javax.inject.Inject

class CitiesMapper @Inject constructor() {

    fun responseToEntity(response: CityResponse): CitiesEntity {
        val str = response.city.lowercase()
        //if (str.contains(' ')) str = str.substring(0, str.indexOf(' ') + 1)
        return CitiesEntity(city = str[0].uppercase() + str.substring(1, str.length))
    }

    fun entityToDefault(entity: CitiesEntity): String = entity.city

}