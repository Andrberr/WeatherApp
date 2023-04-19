package com.example.data.models

import com.squareup.moshi.Json

data class ForecastDayListResponse(
    @Json(name="forecastday") val forecasts: List<ForecastDayResponse>? = null
)
