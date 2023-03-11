package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.Repository
import com.example.weatherapp.domain.models.WeatherInfo
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _weatherLiveData = MutableLiveData<WeatherInfo>()
    val weatherLiveData: LiveData<WeatherInfo> get() = _weatherLiveData

    fun getWeatherInfo() {
        viewModelScope.launch {
            _weatherLiveData.value = repository.getFlightsList()
        }
    }
}