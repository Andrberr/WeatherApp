package com.example.data.sources

import com.example.data.database.dao.cities_dao.CitiesDao
import com.example.data.database.dao.weather_dao.DayWeatherDao
import com.example.data.database.dao.weather_dao.LocationModelDao
import com.example.data.database.dao.weather_dao.WeatherModelDao
import com.example.data.database.entities.CitiesEntity
import com.example.data.database.entities.DayWeatherEntity
import com.example.data.database.entities.LocationModelEntity
import com.example.data.database.entities.WeatherModelEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseSource @Inject constructor(
    private val dayWeatherDao: DayWeatherDao,
    private val locationModelDao: LocationModelDao,
    private val weatherModelDao: WeatherModelDao,
    private val citiesDao: CitiesDao
) {
    suspend fun getLocationModel(city: String) =
        withContext(Dispatchers.IO) { locationModelDao.getCityLocation(city) }

    suspend fun getUniqueCities() = withContext(Dispatchers.IO){locationModelDao.getUniqueCities()}

    suspend fun insertLocationModel(location: LocationModelEntity) =
        withContext(Dispatchers.IO) { locationModelDao.insert(location) }

    suspend fun deleteLocationModel() =
        withContext(Dispatchers.IO) { locationModelDao.delete() }

    suspend fun deleteCityLocation(city: String) =
        withContext(Dispatchers.IO) { locationModelDao.deleteCityLocation(city) }

    suspend fun getWeatherModel(city: String) =
        withContext(Dispatchers.IO) { weatherModelDao.getCurrentCityWeather(city) }

    suspend fun getTemperatureForCity(city: String) = withContext(Dispatchers.IO){weatherModelDao.getTemperatureForCity(city)}

    suspend fun insertWeatherModel(weather: WeatherModelEntity) =
        withContext(Dispatchers.IO) { weatherModelDao.insert(weather) }

    suspend fun deleteWeatherModel() =
        withContext(Dispatchers.IO) { weatherModelDao.delete() }

    suspend fun deleteCurrentCityWeather(city: String) =
        withContext(Dispatchers.IO) { weatherModelDao.deleteCurrentCityWeather(city) }

    suspend fun getDaysWeather(city: String) =
        withContext(Dispatchers.IO) { dayWeatherDao.getDayCityWeather(city) }

    suspend fun insertDaysWeather(weather: List<DayWeatherEntity>) =
        withContext(Dispatchers.IO) { dayWeatherDao.insertAll(weather) }

    suspend fun deleteDaysWeather() =
        withContext(Dispatchers.IO) { dayWeatherDao.deleteAll() }

    suspend fun deleteDayCityWeather(city: String) =
        withContext(Dispatchers.IO) { dayWeatherDao.deleteDayCityWeather(city) }

    suspend fun getCities() = withContext(Dispatchers.IO) { citiesDao.getAll() }

    suspend fun insertCities(cities: List<CitiesEntity>) =
        withContext(Dispatchers.IO) { citiesDao.insert(cities) }

    suspend fun deleteAllCities() = withContext(Dispatchers.IO) { citiesDao.deleteAll() }
}