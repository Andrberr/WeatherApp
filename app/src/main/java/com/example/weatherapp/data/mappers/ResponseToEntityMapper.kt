package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.database.entities.*
import com.example.weatherapp.data.models.weather_models.ForecastDayResponse
import com.example.weatherapp.data.models.weather_models.HourResponse
import com.example.weatherapp.data.models.weather_models.LocationResponse
import com.example.weatherapp.data.models.weather_models.WeatherInfoResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ResponseToEntityMapper @Inject constructor() {
    fun mapToLocationModelEntity(locationResponse: LocationResponse) =
        with(locationResponse) {
            LocationModelEntity(
                city = city ?: "",
                region = region ?: "",
                country = country ?: "",
                latitude = latitude ?: 0f,
                longitude = longitude ?: 0f,
                timeZone = timeZone ?: "",
                time = time ?: "",
            )
        }

    fun mapToWeatherModelEntity(weatherResponse: WeatherInfoResponse, city: String) =
        with(weatherResponse) {
            WeatherModelEntity(
                city = city,
                tempC = tempC ?: 0f,
                tempF = tempF ?: 0f,
                isDay = isDay ?: 0,
                textDescription = weatherCondition?.textDescription ?: "",
                icon = weatherCondition?.icon ?: "",
                windSpeed = windSpeed ?: 0f,
                windDegree = windDegree ?: 0f,
                windDirection = windDirection ?: "",
                pressure = pressure ?: 0f,
                precipitationAmountHour = precipitationAmountHour ?: 0f,
                humidityPercent = humidityPercent ?: 0f,
                cloudPercent = cloudPercent ?: 0f,
                feelingC = feelingC ?: 0f,
                feelingF = feelingF ?: 0f,
                visibilityKm = visibilityKm ?: 0f,
                gustWindSpeed = gustWindSpeed ?: 0f,
            )
        }

    private fun mapToHourModel(hourResponse: HourResponse): HourModelSer {
        return with(hourResponse) {
            HourModelSer(
                time = time ?: "",
                tempC = tempC ?: 0f,
                tempF = tempF ?: 0f,
                isDay = isDay ?: 0,
                textDescription = weatherCondition?.textDescription ?: "",
                icon = weatherCondition?.icon ?: "",
                windSpeed = windSpeed ?: 0f,
                windDegree = windDegree ?: 0f,
                windDirection = windDirection ?: "",
                pressure = pressure ?: 0f,
                precipitationAmountHour = precipitationAmountHour ?: 0f,
                humidityPercent = humidityPercent ?: 0f,
                cloudPercent = cloudPercent ?: 0f,
                feelingC = feelingC ?: 0f,
                feelingF = feelingF ?: 0f,
                visibilityKm = visibilityKm ?: 0f,
                gustWindSpeed = gustWindSpeed ?: 0f,
            )
        }
    }

    fun mapToDayWeatherEntity(response: ForecastDayResponse, city: String): DayWeatherEntity {
        var commonDate = response.date ?: ""
        commonDate = with(commonDate) {
            substring(8, 10) + "." + substring(5, 7) + "." + substring(0, 4)
        }

        var code = ""
        response.hours?.forEach {
            code+=Json.encodeToString(mapToHourModel(it))
        }

        return with(response) {
            DayWeatherEntity(
                city = city,
                date = commonDate,
                maxTempC = day?.maxTempC ?: 0f,
                maxTempF = day?.maxTempF ?: 0f,
                minTempC = day?.minTempC ?: 0f,
                minTempF = day?.minTempF ?: 0f,
                avgTempC = day?.avgTempC ?: 0f,
                avgTempF = day?.avgTempF ?: 0f,
                maxWindSpeed = day?.maxWindSpeed ?: 0f,
                totalPrecipitation = day?.totalPrecipitation ?: 0f,
                avgVisibility = day?.avgVisibility ?: 0f,
                avgHumidity = day?.avgHumidity ?: 0f,
                ultravioletInd = day?.ultravioletInd ?: 0f,
                rainChance = day?.rainChance ?: 0f,
                snowChance = day?.snowChance ?: 0f,
                sunrise = astro?.sunrise ?: "",
                sunset = astro?.sunset ?: "",
                moonrise = astro?.moonrise ?: "",
                moonset = astro?.moonset ?: "",
                moonPhase = astro?.moonPhase ?: "",
                moonIllumination = astro?.moonIllumination ?: 0f,
                textDescription = day?.weatherCondition?.textDescription ?: "",
                icon = day?.weatherCondition?.icon ?: "",
                hourModels = code
            )
        }
    }
}