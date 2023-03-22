package com.example.weatherapp.data.models.weather_models

import com.google.gson.annotations.SerializedName

data class WeatherConditionResponse(
    @SerializedName("text") val textDescription: String? = null,
    @SerializedName("icon") val icon: String? = null
)