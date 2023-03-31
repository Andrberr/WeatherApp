package com.example.data.models.weather_models

import com.google.gson.annotations.SerializedName

data class ForecastDayListResponse(
    @SerializedName("forecastday") val forecasts: List<ForecastDayResponse>? = null
)
