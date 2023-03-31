package com.example.domain.models

data class DayWeather(
    val date: String,
    val maxTempC: Float,
    val maxTempF: Float,
    val minTempC: Float,
    val minTempF: Float,
    val avgTempC: Float,
    val avgTempF: Float,
    val maxWindSpeed: Float,
    val totalPrecipitation: Float,
    val avgVisibility: Float,
    val avgHumidity: Float,
    val ultravioletInd: Float,
    val rainChance: Float,
    val snowChance: Float,
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moonPhase: String,
    val moonIllumination: Float,
    val textDescription: String,
    val icon: String,
    val hourWeathers: List<HourModel>
)
