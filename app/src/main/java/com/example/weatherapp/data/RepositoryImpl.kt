package com.example.weatherapp.data

import com.example.weatherapp.data.mappers.WeatherMapper
import com.example.weatherapp.data.network.WeatherService
import com.example.weatherapp.domain.Repository
import com.example.weatherapp.domain.models.WeatherInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: WeatherService,
    private val mapper: WeatherMapper
) : Repository {

    override suspend fun getWeatherInfo(): WeatherInfo {
        return withContext(Dispatchers.IO) {
            val response =
                (service.getWeatherResponse("Minsk", 14)
                    .execute().body()
                    ?: throw Exception())
            mapper(response)
        }
    }
}