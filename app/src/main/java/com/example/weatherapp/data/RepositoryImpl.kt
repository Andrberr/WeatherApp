package com.example.weatherapp.data

import com.example.weatherapp.data.mappers.CitiesMapper
import com.example.weatherapp.data.mappers.EntityToDefaultMapper
import com.example.weatherapp.data.mappers.ResponseToDefaultMapper
import com.example.weatherapp.data.mappers.ResponseToEntityMapper
import com.example.weatherapp.data.network.CitiesService
import com.example.weatherapp.data.network.WeatherService
import com.example.weatherapp.data.sources.DataBaseSource
import com.example.weatherapp.domain.Repository
import com.example.weatherapp.domain.models.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val citiesService: CitiesService,
    private val responseToDefaultMapper: ResponseToDefaultMapper,
    private val responseToEntityMapper: ResponseToEntityMapper,
    private val entityToDefaultMapper: EntityToDefaultMapper,
    private val citiesMapper: CitiesMapper,
    private val dataBaseSource: DataBaseSource
) : Repository {
    override suspend fun getWeatherInfo(cache: Boolean, city: String): WeatherInfo {
        return withContext(Dispatchers.IO) {
            if (cache) {
                val response =
                    (weatherService.getWeatherResponse(city, 14)
                        .execute().body()
                        ?: throw Exception())
                with(dataBaseSource) {
                    if (getDaysWeather().isNotEmpty()) {
                        deleteDaysWeather()
                        deleteWeatherModel()
                        deleteLocationModel()
                    }
                }
                if (response.location != null) {
                    dataBaseSource.insertLocationModel(
                        responseToEntityMapper.mapToLocationModelEntity(
                            response.location
                        )
                    )

                    if (response.location.city != null) {
                        val list = response.daysForecasts?.forecasts?.map {
                            responseToEntityMapper.mapToDayWeatherEntity(it, response.location.city)
                        }
                        if (list != null) dataBaseSource.insertDaysWeather(list)
                    }
                }
                if (response.currentWeather != null) {
                    dataBaseSource.insertWeatherModel(
                        responseToEntityMapper.mapToWeatherModelEntity(
                            response.currentWeather
                        )
                    )
                }

                responseToDefaultMapper(response)
            } else {
                with(dataBaseSource) {
                    entityToDefaultMapper(getLocationModel(), getWeatherModel(), getDaysWeather())
                }
            }
        }
    }

    override suspend fun getCities(cache: Boolean): List<String> {
        return withContext(Dispatchers.IO) {
            if (dataBaseSource.getCities().isEmpty()) {
                val response =
                    (citiesService.getCitiesResponse()
                        .execute().body()
                        ?: throw Exception())
                val cities =
                    citiesMapper.sort(response.citiesList.map { citiesMapper.responseToEntity(it) })
                dataBaseSource.insertCities(cities.subList(1, cities.size))
            }
            dataBaseSource.getCities().map { citiesMapper.entityToDefault(it) }
        }
    }
}