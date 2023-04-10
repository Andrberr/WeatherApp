package com.example.data.models

import com.squareup.moshi.Json

data class HourResponse(
    @Json(name = "time") val time: String? = null,
    @Json(name = "temp_c") val tempC: Float? = null,
    @Json(name = "temp_f") val tempF: Float? = null,
    @Json(name = "is_day") val isDay: Int? = null,
    @Json(name = "condition") val weatherCondition: WeatherConditionResponse? = null,
    @Json(name = "wind_kph") val windSpeed: Float? = null,
    @Json(name = "wind_degree") val windDegree: Float? = null,
    @Json(name = "wind_dir") val windDirection: String? = null,
    @Json(name = "pressure_in") val pressure: Float? = null,
    @Json(name = "precip_mm") val precipitationAmountHour: Float? = null,
    @Json(name = "humidity") val humidityPercent: Float? = null,
    @Json(name = "cloud") val cloudPercent: Float? = null,
    @Json(name = "feelslike_c") val feelingC: Float? = null,
    @Json(name = "feelslike_f") val feelingF: Float? = null,
    @Json(name = "vis_km") val visibilityKm: Float? = null,
    @Json(name = "gust_kph") val gustWindSpeed: Float? = null,
)
