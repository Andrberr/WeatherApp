package com.example.weatherapp.domain.models

data class WeatherInfo(
    val city: String,
    val region: String,
    val country: String,
    val latitude: Float,
    val longitude: Float,
    val timeZone: String,
    val time: String,
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
    val ultravioletInd: Float,
    val gustWindSpeed: Float
)