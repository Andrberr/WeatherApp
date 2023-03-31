package com.example.domain.models

data class WeatherInfo(
    val location: LocationModel,
    val currentWeather: WeatherModel,
    val daysForecasts: List<DayWeather>
)