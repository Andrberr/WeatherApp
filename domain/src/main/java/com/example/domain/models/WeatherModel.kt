package com.example.domain.models

data class WeatherModel(
    val tempC: Float,
    val tempF: Float,
    val isDay: Int,
    val textDescription: String,
    val icon: String,
    val windSpeed: Float,
    val windDegree: Float,
    val windDirection: String,
    val pressure: Float,
    val precipitationAmountHour: Float,
    val humidityPercent: Float,
    val cloudPercent: Float,
    val feelingC: Float,
    val feelingF: Float,
    val visibilityKm: Float,
    val gustWindSpeed: Float
)
