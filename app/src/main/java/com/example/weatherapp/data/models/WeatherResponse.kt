package com.example.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val location: LocationResponse? = null,
    @SerializedName("current") val currentWeather: WeatherInfoResponse? = null,
    @SerializedName("forecast") val daysForecasts: ForecastDayListResponse? = null
)
