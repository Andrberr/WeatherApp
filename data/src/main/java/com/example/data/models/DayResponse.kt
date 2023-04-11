package com.example.data.models

import com.squareup.moshi.Json

data class DayResponse(
    @Json(name = "maxtemp_c") val maxTempC: Float? = null,
    @Json(name = "maxtemp_f") val maxTempF: Float? = null,
    @Json(name = "mintemp_c") val minTempC: Float? = null,
    @Json(name = "mintemp_f") val minTempF: Float? = null,
    @Json(name = "avgtemp_c") val avgTempC: Float? = null,
    @Json(name = "avgtemp_f") val avgTempF: Float? = null,
    @Json(name = "maxwind_kph") val maxWindSpeed: Float? = null,
    @Json(name = "totalprecip_mm") val totalPrecipitation: Float? = null,
    @Json(name = "avgvis_km") val avgVisibility: Float? = null,
    @Json(name = "avghumidity") val avgHumidity: Float? = null,
    @Json(name = "uv") val ultravioletInd: Float? = null,
    @Json(name = "daily_chance_of_rain") val rainChance: Float? = null,
    @Json(name = "daily_chance_of_snow") val snowChance: Float? = null,
    @Json(name = "condition") val weatherCondition: WeatherConditionResponse? = null,
)
