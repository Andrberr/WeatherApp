package com.example.weatherapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_weather_table")
data class DayWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "maxTempC") val maxTempC: Float,
    @ColumnInfo(name = "maxTempF") val maxTempF: Float,
    @ColumnInfo(name = "minTempC") val minTempC: Float,
    @ColumnInfo(name = "minTempF") val minTempF: Float,
    @ColumnInfo(name = "avgTempC") val avgTempC: Float,
    @ColumnInfo(name = "avgTempF") val avgTempF: Float,
    @ColumnInfo(name = "maxWindSpeed") val maxWindSpeed: Float,
    @ColumnInfo(name = "totalPrecipitation") val totalPrecipitation: Float,
    @ColumnInfo(name = "avgVisibility") val avgVisibility: Float,
    @ColumnInfo(name = "avgHumidity") val avgHumidity: Float,
    @ColumnInfo(name = "ultravioletInd") val ultravioletInd: Float,
    @ColumnInfo(name = "rainChance") val rainChance: Float,
    @ColumnInfo(name = "snowChance") val snowChance: Float,
    @ColumnInfo(name = "sunrise") val sunrise: String,
    @ColumnInfo(name = "sunset") val sunset: String,
    @ColumnInfo(name = "moonrise") val moonrise: String,
    @ColumnInfo(name = "moonset") val moonset: String,
    @ColumnInfo(name = "moonPhase") val moonPhase: String,
    @ColumnInfo(name = "moonIllumination") val moonIllumination: Float,
    @ColumnInfo(name = "textDescription") val textDescription: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "hourWeathers") val hourWeathers: List<HourModel>
)