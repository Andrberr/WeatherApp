package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AddedCityInfo
import com.example.domain.repositories.CitiesRepository
import com.example.domain.repositories.WeatherRepository
import com.example.weatherapp.di.FragmentScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository,
) : ViewModel() {

    private val _addedCitiesLiveData = MutableLiveData<List<AddedCityInfo>>()
    val addedCitiesLiveData: LiveData<List<AddedCityInfo>> get() = _addedCitiesLiveData

    private val _userCityLiveData = MutableLiveData<String>()
    val userCityLiveData: LiveData<String> get() = _userCityLiveData

    private val _rememberCityLiveData = MutableLiveData<String>()
    val rememberCityLiveData: LiveData<String> get() = _rememberCityLiveData

    private val _citiesLiveData = MutableLiveData<List<String>>()
    val citiesLiveData: LiveData<List<String>> get() = _citiesLiveData

    fun getAddedCities() {
        viewModelScope.launch {
            _addedCitiesLiveData.value = citiesRepository.getAddedCitiesInfo()
        }
    }

    fun getUserCity() {
        _userCityLiveData.value = citiesRepository.getUserCity(CITY_KEY)
    }

    fun setUserCity(city: String) {
        citiesRepository.setUserCity(city, CITY_KEY)
    }

    fun getRememberCity() {
        _rememberCityLiveData.value = citiesRepository.getUserCity(REMEMBER_CITY_KEY)
    }

    fun setRememberCity(city: String) {
        citiesRepository.setUserCity(city, REMEMBER_CITY_KEY)
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

    companion object {
        private const val CITY_KEY = "CITY"
        private const val REMEMBER_CITY_KEY = "REM_CITY"
    }
}