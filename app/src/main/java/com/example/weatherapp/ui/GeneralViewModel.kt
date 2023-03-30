package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.di.FragmentScope
import com.example.weatherapp.domain.Repository
import com.example.weatherapp.domain.models.AddedCityInfo
import com.example.weatherapp.domain.models.WeatherInfo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class GeneralViewModel @Inject constructor(
    private val repository: Repository,
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
            _weatherLiveData.value = repository.getWeatherInfo(false, city)
        }
    }

    fun getWeatherInfo(city: String) {
        this.city = city
        viewModelScope.launch(handler) {
            _weatherLiveData.value = repository.getWeatherInfo(true, city)
        }
    }

    fun getCities() {
        viewModelScope.launch {
            _citiesLiveData.value = repository.getCities(true)
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

    fun getAddedCities(){
        viewModelScope.launch {
            _addedCitiesLiveData.value = repository.getAddedCitiesInfo()
        }
    }

    fun getUserCity(){
        _userCityLiveData.value = repository.getUserCity()
    }

    fun setUserCity(city: String){
        repository.setUserCity(city)
    }
}