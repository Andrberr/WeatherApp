package com.example.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class HourResponse(
    @SerializedName("time") val time: String? = null,
    @SerializedName("temp_c") val tempC: Float? = null,
    @SerializedName("temp_f") val tempF: Float? = null,
    @SerializedName("is_day") val isDay: Int? = null,
    @SerializedName("condition") val weatherCondition: WeatherConditionResponse? = null,
    @SerializedName("wind_kph") val windSpeed: Float? = null,
    @SerializedName("wind_degree") val windDegree: Float? = null,
    @SerializedName("wind_dir") val windDirection: String? = null,
    @SerializedName("pressure_in") val pressure: Float? = null,
    @SerializedName("precip_mm") val precipitationAmountHour: Float? = null,
    @SerializedName("humidity") val humidityPercent: Float? = null,
    @SerializedName("cloud") val cloudPercent: Float? = null,
    @SerializedName("feelslike_c") val feelingC: Float? = null,
    @SerializedName("feelslike_f") val feelingF: Float? = null,
    @SerializedName("vis_km") val visibilityKm: Float? = null,
    @SerializedName("gust_kph") val gustWindSpeed: Float? = null,
)
