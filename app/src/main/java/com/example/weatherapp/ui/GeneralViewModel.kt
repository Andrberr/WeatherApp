package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CitiesRepository
import com.example.weatherapp.di.FragmentScope
import com.example.domain.WeatherRepository
import com.example.domain.models.AddedCityInfo
import com.example.domain.models.WeatherInfo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class GeneralViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val citiesRepository: CitiesRepository
) : ViewModel() {
    private val _weatherLiveData = MutableLiveData<WeatherInfo>()
    val weatherLiveData: LiveData<WeatherInfo> get() = _weatherLiveData

    private val _citiesLiveData = MutableLiveData<List<String>>()
    val citiesLiveData: LiveData<List<String>> get() = _citiesLiveData

    private val _addedCitiesLiveData = MutableLiveData<List<AddedCityInfo>>()
    val addedCitiesLiveData: LiveData<List<AddedCityInfo>> get() = _addedCitiesLiveData

    private val _userCityLiveData = MutableLiveData<String>()
    val userCityLiveData: LiveData<String> get() = _userCityLiveData

    private lateinit var city: String

    private val handler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _weatherLiveData.value = weatherRepository.getWeatherInfo(false, city)
        }
    }

    fun getWeatherInfo(city: String) {
        this.city = city
        viewModelScope.launch(handler) {
            _weatherLiveData.value = weatherRepository.getWeatherInfo(true, city)
        }
    }

    fun getCities() {
        viewModelScope.launch {
            _citiesLiveData.value = citiesRepository.getCities(true)
        }
    }

    fun searchForCities(list: List<String>, findStr: String): List<String> {
        val resultList = mutableListOf<String>()
        for (elem in list) {
            if (elem.startsWith(findStr)) {
                resultList.add(elem)
            }
        }
        return resultList
    }

    fun getAddedCities() {
        viewModelScope.launch {
            _addedCitiesLiveData.value = citiesRepository.getAddedCitiesInfo()
        }
    }

    fun getUserCity() {
        _userCityLiveData.value = citiesRepository.getUserCity()
    }

    fun setUserCity(city: String) {
        citiesRepository.setUserCity(city)
    }

    fun deleteCityFromDataBase(city: String) {
        viewModelScope.launch {
            weatherRepository.deleteCityFromDatabase(city)
        }
    }
}