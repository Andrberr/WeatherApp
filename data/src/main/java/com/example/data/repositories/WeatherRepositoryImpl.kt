package com.example.data.repositories

import com.example.data.mappers.EntityToDefaultMapper
import com.example.data.mappers.ResponseToDefaultMapper
import com.example.data.mappers.ResponseToEntityMapper
import com.example.data.network.WeatherService
import com.example.data.sources.DataBaseSource
import com.example.domain.repositories.WeatherRepository
import com.example.domain.models.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val responseToDefaultMapper: ResponseToDefaultMapper,
    private val responseToEntityMapper: ResponseToEntityMapper,
    private val entityToDefaultMapper: EntityToDefaultMapper,
    private val dataBaseSource: DataBaseSource,
) : WeatherRepository {
    override suspend fun getWeatherInfo(cache: Boolean, city: String): WeatherInfo {
        return withContext(Dispatchers.IO) {
            if (cache) {
                val response =
                    (weatherService.getWeatherResponse(city, 14)
                        .execute().body()
                        ?: throw Exception())
                deleteCityFromDatabase(city)

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

                        if (response.currentWeather != null) {
                            dataBaseSource.insertWeatherModel(
                                responseToEntityMapper.mapToWeatherModelEntity(
                                    response.currentWeather, response.location.city
                                )
                            )
                        }
                    }
                }

                responseToDefaultMapper(response)
            } else {
                with(dataBaseSource) {
                    entityToDefaultMapper(
                        getLocationModel(city),
                        getWeatherModel(city),
                        getDaysWeather(city)
                    )
                }
            }
        }
    }

    override suspend fun deleteCityFromDatabase(city: String){
        with(dataBaseSource) {
            if (getDaysWeather(city).isNotEmpty()) {
                deleteCityLocation(city)
                deleteCurrentCityWeather(city)
                deleteDayCityWeather(city)
            }
        }
    }
}