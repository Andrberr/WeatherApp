package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.di.FragmentScope
import com.example.domain.repositories.WeatherRepository
import com.example.domain.models.DayWeather
import com.example.domain.models.WeatherInfo
import com.example.domain.models.WeatherModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class GeneralViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private val _weatherLiveData = MutableLiveData<WeatherInfo?>()
    val weatherLiveData: LiveData<WeatherInfo?> get() = _weatherLiveData

    private val _hourWeatherLiveData = MutableLiveData<WeatherModel>()
    val hourWeatherLiveData: LiveData<WeatherModel> get() = _hourWeatherLiveData

    private val _dayWeatherLiveData = MutableLiveData<DayWeather>()
    val dayWeatherLiveData: LiveData<DayWeather> get() = _dayWeatherLiveData

    private lateinit var city: String
    private lateinit var coordinates: String

    private val handler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _weatherLiveData.value = weatherRepository.getWeatherInfo(false, city, coordinates)
        }
    }

    fun getWeatherInfo(city: String, coordinates: String) {
        this.city = city
        this.coordinates = coordinates
        viewModelScope.launch(handler) {
            _weatherLiveData.value = weatherRepository.getWeatherInfo(true, city, coordinates)
        }
    }

    fun getWeatherFromDataBase(city: String) {
        viewModelScope.launch() {
            _weatherLiveData.value = weatherRepository.getWeatherInfo(false, city, "")
        }
    }

    fun getHourWeatherInfo(hourModel: WeatherModel) {
        _hourWeatherLiveData.value = hourModel
    }

    fun getDayWeather(dayWeather: DayWeather) {
        _dayWeatherLiveData.value = dayWeather
    }

    fun deleteCityFromDataBase(city: String) {
        viewModelScope.launch {
            weatherRepository.deleteCityFromDatabase(city)
        }
    }
}