package com.example.data.models

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name="location") val location: LocationResponse? = null,
    @Json(name="current") val currentWeather: WeatherInfoResponse? = null,
    @Json(name="forecast") val daysForecasts: ForecastDayListResponse? = null
)
