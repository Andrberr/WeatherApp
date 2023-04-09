package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repositories.CitiesRepository
import com.example.weatherapp.di.FragmentScope
import com.example.domain.repositories.WeatherRepository
import com.example.domain.models.AddedCityInfo
import com.example.domain.models.DayWeather
import com.example.domain.models.WeatherInfo
import com.example.domain.models.WeatherModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class GeneralViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val citiesRepository: CitiesRepository,
) : ViewModel() {
    private val _weatherLiveData = MutableLiveData<WeatherInfo>()
    val weatherLiveData: LiveData<WeatherInfo> get() = _weatherLiveData

    private val _hourWeatherLiveData = MutableLiveData<WeatherModel>()
    val hourWeatherLiveData: LiveData<WeatherModel> get() = _hourWeatherLiveData

    private val _dayWeatherLiveData = MutableLiveData<DayWeather>()
    val dayWeatherLiveData: LiveData<DayWeather> get() = _dayWeatherLiveData

    private val _locationLiveData = MutableLiveData<String>()
    val locationLiveData: LiveData<String> get() = _locationLiveData

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

    fun getHourWeatherInfo(hourModel: WeatherModel) {
        _hourWeatherLiveData.value = hourModel
    }

    fun getDayWeather(dayWeather: DayWeather) {
        _dayWeatherLiveData.value = dayWeather
    }

}