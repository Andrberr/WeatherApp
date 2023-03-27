package com.example.weatherapp.data.sources

import com.example.weatherapp.data.database.dao.cities_dao.CitiesDao
import com.example.weatherapp.data.database.dao.weather_dao.DayWeatherDao
import com.example.weatherapp.data.database.dao.weather_dao.LocationModelDao
import com.example.weatherapp.data.database.dao.weather_dao.WeatherModelDao
import com.example.weatherapp.data.database.entities.CitiesEntity
import com.example.weatherapp.data.database.entities.DayWeatherEntity
import com.example.weatherapp.data.database.entities.LocationModelEntity
import com.example.weatherapp.data.database.entities.WeatherModelEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseSource @Inject constructor(
    private val dayWeatherDao: DayWeatherDao,
    private val locationModelDao: LocationModelDao,
    private val weatherModelDao: WeatherModelDao,
    private val citiesDao: CitiesDao
) {
    suspend fun getLocationModel() = withContext(Dispatchers.IO) { locationModelDao.getAll() }
    suspend fun insertLocationModel(location: LocationModelEntity) =
        withContext(Dispatchers.IO) { locationModelDao.insert(location) }

    suspend fun deleteLocationModel() =
        withContext(Dispatchers.IO) { locationModelDao.delete() }

    suspend fun getWeatherModel() = withContext(Dispatchers.IO) { weatherModelDao.getAll() }
    suspend fun insertWeatherModel(weather: WeatherModelEntity) =
        withContext(Dispatchers.IO) { weatherModelDao.insert(weather) }

    suspend fun deleteWeatherModel() =
        withContext(Dispatchers.IO) { weatherModelDao.delete() }

    suspend fun getDaysWeather() = withContext(Dispatchers.IO) { dayWeatherDao.getAll() }
    suspend fun insertDaysWeather(weather: List<DayWeatherEntity>) =
        withContext(Dispatchers.IO) { dayWeatherDao.insertAll(weather) }

    suspend fun deleteDaysWeather() =
        withContext(Dispatchers.IO) { dayWeatherDao.deleteAll() }

    suspend fun getCities() = withContext(Dispatchers.IO) { citiesDao.getAll() }

    suspend fun insertCities(cities: List<CitiesEntity>) =
        withContext(Dispatchers.IO) { citiesDao.insert(cities) }

    suspend fun deleteAllCities() = withContext(Dispatchers.IO) { citiesDao.deleteAll() }
}