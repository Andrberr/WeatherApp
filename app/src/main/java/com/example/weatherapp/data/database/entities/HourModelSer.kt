package com.example.weatherapp.data.database.entities
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourModelSer(
    @SerialName("time") val time: String,
    @SerialName("tempC") val tempC: Float,
    @SerialName("tempF") val tempF: Float,
    @SerialName("isDay") val isDay: Int,
    @SerialName("textDescription") val textDescription: String,
    @SerialName("icon") val icon: String,
    @SerialName("windSpeed") val windSpeed: Float,
    @SerialName("windDegree") val windDegree: Float,
    @SerialName("windDirection") val windDirection: String,
    @SerialName("pressure") val pressure: Float,
    @SerialName("precipitationAmountHour") val precipitationAmountHour: Float,
    @SerialName("humidityPercent") val humidityPercent: Float,
    @SerialName("cloudPercent") val cloudPercent: Float,
    @SerialName("feelingC") val feelingC: Float,
    @SerialName("feelingF") val feelingF: Float,
    @SerialName("visibilityKm") val visibilityKm: Float,
    @SerialName("gustWindSpeed") val gustWindSpeed: Float
)
