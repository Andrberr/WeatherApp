package com.example.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class ForecastDayListResponse(
    @SerializedName("forecastday") val forecasts: List<ForecastDayResponse>? = null
)
