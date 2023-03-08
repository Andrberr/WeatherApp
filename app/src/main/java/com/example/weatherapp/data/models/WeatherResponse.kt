package com.example.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val locationInfo: LocationResponse? = null,
    @SerializedName("current") val weather: WeatherInfoResponse? = null
)
