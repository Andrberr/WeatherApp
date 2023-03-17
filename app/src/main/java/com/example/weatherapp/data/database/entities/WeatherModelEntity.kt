package com.example.weatherapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather_table")
data class WeatherModelEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "tempC") val tempC: Float,
    @ColumnInfo(name = "tempF") val tempF: Float,
    @ColumnInfo(name = "isDay") val isDay: Int,
    @ColumnInfo(name = "textDescription") val textDescription: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "windSpeed") val windSpeed: Float,
    @ColumnInfo(name = "windDegree") val windDegree: Float,
    @ColumnInfo(name = "windDirection") val windDirection: String,
    @ColumnInfo(name = "pressure") val pressure: Float,
    @ColumnInfo(name = "precipitationAmountHour") val precipitationAmountHour: Float,
    @ColumnInfo(name = "humidityPercent") val humidityPercent: Float,
    @ColumnInfo(name = "cloudPercent") val cloudPercent: Float,
    @ColumnInfo(name = "feelingC") val feelingC: Float,
    @ColumnInfo(name = "feelingF") val feelingF: Float,
    @ColumnInfo(name = "visibilityKm") val visibilityKm: Float,
    @ColumnInfo(name = "gustWindSpeed") val gustWindSpeed: Float
)