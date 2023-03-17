package com.example.weatherapp.data.sources

import com.example.weatherapp.data.database.dao.DayWeatherDao
import com.example.weatherapp.data.database.dao.LocationModelDao
import com.example.weatherapp.data.database.dao.WeatherModelDao
import com.example.weatherapp.data.database.entities.DayWeatherEntity
import com.example.weatherapp.data.database.entities.LocationModelEntity
import com.example.weatherapp.data.database.entities.WeatherModelEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseSource @Inject constructor(
    private val dayWeatherDao: DayWeatherDao,
    private val locationModelDao: LocationModelDao,
    private val weatherModelDao: WeatherModelDao
) {
    suspend fun getLocationModel() = withContext(Dispatchers.IO) { locationModelDao.getAll() }
    suspend fun insertLocationModel(location: LocationModelEntity) =
        withContext(Dispatchers.IO) { locationModelDao.insert(location) }

    suspend fun deleteLocationModel(location: LocationModelEntity) =
        withContext(Dispatchers.IO) { locationModelDao.delete(location) }

    suspend fun getWeatherModel() = withContext(Dispatchers.IO) { weatherModelDao.getAll() }
    suspend fun insertWeatherModel(weather: WeatherModelEntity) =
        withContext(Dispatchers.IO) { weatherModelDao.insert(weather) }

    suspend fun deleteWeatherModel(weather: WeatherModelEntity) =
        withContext(Dispatchers.IO) { weatherModelDao.delete(weather) }

    suspend fun getDaysWeather() = withContext(Dispatchers.IO) { dayWeatherDao.getAll() }
    suspend fun insertDaysWeather(weather: List<DayWeatherEntity>) =
        withContext(Dispatchers.IO) { dayWeatherDao.insertAll(weather) }

    suspend fun deleteDaysWeather(weather: List<DayWeatherEntity>) =
        withContext(Dispatchers.IO) { dayWeatherDao.deleteAll(weather) }
}