package com.example.weatherapp.domain.models

data class WeatherInfo(
    val location: LocationModel,
    val currentWeather: WeatherModel,
    val daysForecasts: List<DayWeather>
)