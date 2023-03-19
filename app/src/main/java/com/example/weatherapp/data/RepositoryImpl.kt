package com.example.weatherapp.data

import com.example.weatherapp.data.mappers.EntityToDefaultMapper
import com.example.weatherapp.data.mappers.ResponseToDefaultMapper
import com.example.weatherapp.data.mappers.ResponseToEntityMapper
import com.example.weatherapp.data.network.WeatherService
import com.example.weatherapp.data.sources.DataBaseSource
import com.example.weatherapp.domain.Repository
import com.example.weatherapp.domain.models.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: WeatherService,
    private val responseToDefaultMapper: ResponseToDefaultMapper,
    private val responseToEntityMapper: ResponseToEntityMapper,
    private val entityToDefaultMapper: EntityToDefaultMapper,
    private val dataBaseSource: DataBaseSource
) : Repository {

    override suspend fun getWeatherInfo(cache: Boolean): WeatherInfo {
        return withContext(Dispatchers.IO) {
            if (cache) {
                val response =
                    (service.getWeatherResponse("Minsk", 14)
                        .execute().body()
                        ?: throw Exception())
                with(dataBaseSource) {
                    deleteDaysWeather(getDaysWeather())
                    deleteWeatherModel(getWeatherModel())
                    deleteLocationModel(getLocationModel())
                }
                if (response.location != null) dataBaseSource.insertLocationModel(
                    responseToEntityMapper.mapToLocationModelEntity(
                        response.location
                    )
                )
                if (response.currentWeather != null) {
                    dataBaseSource.insertWeatherModel(
                        responseToEntityMapper.mapToWeatherModelEntity(
                            response.currentWeather
                        )
                    )
                }
                val list = response.daysForecasts?.forecasts?.map {
                    responseToEntityMapper.mapToDayWeatherEntity(it)
                }
                if (list != null) dataBaseSource.insertDaysWeather(list)

                responseToDefaultMapper(response)
            } else {
                with(dataBaseSource) {
                    entityToDefaultMapper(getLocationModel(), getWeatherModel(), getDaysWeather())
                }
            }
        }
    }
}