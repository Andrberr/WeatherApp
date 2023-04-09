package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AddedCityInfo
import com.example.domain.repositories.CitiesRepository
import com.example.domain.repositories.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val weatherRepository: WeatherRepository
): ViewModel()  {

    private val _addedCitiesLiveData = MutableLiveData<List<AddedCityInfo>>()
    val addedCitiesLiveData: LiveData<List<AddedCityInfo>> get() = _addedCitiesLiveData

    private val _userCityLiveData = MutableLiveData<String>()
    val userCityLiveData: LiveData<String> get() = _userCityLiveData

    private val _citiesLiveData = MutableLiveData<List<String>>()
    val citiesLiveData: LiveData<List<String>> get() = _citiesLiveData

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

    fun deleteCityFromDataBase(city: String) {
        viewModelScope.launch {
            weatherRepository.deleteCityFromDatabase(city)
        }
    }
}