package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.di.FragmentScope
import com.example.weatherapp.domain.Repository
import com.example.weatherapp.domain.models.WeatherInfo
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class WeatherViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private val _weatherLiveData = MutableLiveData<WeatherInfo>()
    val weatherLiveData: LiveData<WeatherInfo> get() = _weatherLiveData

    private val _citiesLiveData = MutableLiveData<List<String>>()
    val citiesLiveData: LiveData<List<String>> get() = _citiesLiveData

    fun getWeatherInfo(city: String) {
        viewModelScope.launch {
            _weatherLiveData.value = repository.getWeatherInfo(true, city)
        }
    }

    fun getCities(){
        viewModelScope.launch {
            _citiesLiveData.value = repository.getCities(true)
        }
    }
}