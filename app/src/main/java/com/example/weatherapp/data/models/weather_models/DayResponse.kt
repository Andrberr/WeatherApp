package com.example.weatherapp.data.models.weather_models

import com.google.gson.annotations.SerializedName

data class DayResponse(
    @SerializedName("maxtemp_c") val maxTempC: Float? = null,
    @SerializedName("maxtemp_f") val maxTempF: Float? = null,
    @SerializedName("mintemp_c") val minTempC: Float? = null,
    @SerializedName("mintemp_f") val minTempF: Float? = null,
    @SerializedName("avgtemp_c") val avgTempC: Float? = null,
    @SerializedName("avgtemp_f") val avgTempF: Float? = null,
    @SerializedName("maxwind_kph") val maxWindSpeed: Float? = null,
    @SerializedName("totalprecip_mm") val totalPrecipitation: Float? = null,
    @SerializedName("avgvis_km") val avgVisibility: Float? = null,
    @SerializedName("avghumidity") val avgHumidity: Float? = null,
    @SerializedName("uv") val ultravioletInd: Float? = null,
    @SerializedName("daily_chance_of_rain") val rainChance: Float? = null,
    @SerializedName("daily_chance_of_snow") val snowChance: Float? = null,
    @SerializedName("condition") val weatherCondition: WeatherConditionResponse? = null,
)
