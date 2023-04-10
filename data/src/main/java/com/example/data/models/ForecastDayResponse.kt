package com.example.data.models

import com.squareup.moshi.Json

data class ForecastDayResponse(
    @Json(name = "date") val date: String? = null,
    @Json(name = "day") val day: DayResponse? = null,
    @Json(name = "astro") val astro: AstroResponse? = null,
    @Json(name = "hour") val hours: List<HourResponse>? = null,
)
