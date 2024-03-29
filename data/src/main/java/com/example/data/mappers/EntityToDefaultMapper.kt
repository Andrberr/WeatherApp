package com.example.data.mappers

import com.example.data.database.entities.DayWeatherEntity
import com.example.data.database.entities.HourModelSer
import com.example.data.database.entities.LocationModelEntity
import com.example.data.database.entities.WeatherModelEntity
import com.example.domain.models.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class EntityToDefaultMapper @Inject constructor() {
    operator fun invoke(
        location: LocationModelEntity,
        curWeather: WeatherModelEntity,
        daysWeather: List<DayWeatherEntity>
    ): WeatherInfo {
        return WeatherInfo(
            location = mapToLocationModel(location),
            currentWeather = mapToWeatherModel(curWeather),
            daysForecasts = daysWeather.map { mapToDayWeather(it) }
        )
    }

    private fun mapToLocationModel(location: LocationModelEntity) =
        with(location) {
            LocationModel(
                city = city,
                region = region,
                country = country,
                latitude = latitude,
                longitude = longitude,
                timeZone = timeZone,
                time = time,
            )
        }

    private fun mapToWeatherModel(weather: WeatherModelEntity) =
        with(weather) {
            WeatherModel(
                tempC = tempC,
                tempF = tempF,
                isDay = isDay,
                textDescription = textDescription,
                icon = icon,
                windSpeed = windSpeed,
                windDegree = windDegree,
                windDirection = windDirection,
                pressure = pressure,
                precipitationAmountHour = precipitationAmountHour,
                humidityPercent = humidityPercent,
                cloudPercent = cloudPercent,
                feelingC = feelingC,
                feelingF = feelingF,
                visibilityKm = visibilityKm,
                gustWindSpeed = gustWindSpeed,
            )
        }

    private fun mapToHourModel(hour: HourModelSer): HourModel {
        return with(hour) {
            HourModel(
                time = time,
                weather = WeatherModel(
                    tempC = tempC,
                    tempF = tempF,
                    isDay = isDay,
                    textDescription = textDescription,
                    icon = icon,
                    windSpeed = windSpeed,
                    windDegree = windDegree,
                    windDirection = windDirection,
                    pressure = pressure,
                    precipitationAmountHour = precipitationAmountHour,
                    humidityPercent = humidityPercent,
                    cloudPercent = cloudPercent,
                    feelingC = feelingC,
                    feelingF = feelingF,
                    visibilityKm = visibilityKm,
                    gustWindSpeed = gustWindSpeed,
                )
            )
        }
    }

    private fun mapToDayWeather(dayWeather: DayWeatherEntity): DayWeather {
        val hourModelsList = mutableListOf<HourModel>()
        val code = dayWeather.hourModels
        var i = 0
        while (i < code.length) {
            if (code[i] == OPEN_BRACKET) {
                var obj = ""
                while (code[i] != CLOSE_BRACKET) {
                    obj += code[i]
                    i++
                }
                obj += CLOSE_BRACKET
                hourModelsList.add(mapToHourModel(Json.decodeFromString(obj)))
            }
            i++
        }

        return with(dayWeather) {
            DayWeather(
                date = date,
                maxTempC = maxTempC,
                maxTempF = maxTempF,
                minTempC = minTempC,
                minTempF = minTempF,
                avgTempC = avgTempC,
                avgTempF = avgTempF,
                maxWindSpeed = maxWindSpeed,
                totalPrecipitation = totalPrecipitation,
                avgVisibility = avgVisibility,
                avgHumidity = avgHumidity,
                ultravioletInd = ultravioletInd,
                rainChance = rainChance,
                snowChance = snowChance,
                sunrise = sunrise,
                sunset = sunset,
                moonrise = moonrise,
                moonset = moonset,
                moonPhase = moonPhase,
                moonIllumination = moonIllumination,
                textDescription = textDescription,
                icon = icon,
                hourWeathers = hourModelsList
            )
        }
    }

    companion object {
        private const val OPEN_BRACKET = '{'
        private const val CLOSE_BRACKET = '}'
    }
}