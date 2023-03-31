package com.example.data.models.weather_models

import com.google.gson.annotations.SerializedName

data class ForecastDayResponse(
    @SerializedName("date") val date: String? = null,
    @SerializedName("day") val day: DayResponse? = null,
    @SerializedName("astro") val astro: AstroResponse? = null,
    @SerializedName("hour") val hours: List<HourResponse>? = null,
)
