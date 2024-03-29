package com.example.data.mappers

import com.example.data.models.*
import com.example.domain.models.*
import javax.inject.Inject

class ResponseToDefaultMapper @Inject constructor() {
    operator fun invoke(response: WeatherResponse) = with(response) {
        WeatherInfo(
            location = if (location != null) mapToLocationModel(location) else LocationModel(
                "",
                "",
                "",
                0f,
                0f,
                "",
                ""
            ),
            currentWeather = if (currentWeather != null) mapToWeatherModel(currentWeather) else WeatherModel(
                0f,
                0f,
                0,
                "",
                "",
                0f,
                0f,
                "",
                0f,
                0f,
                0f,
                0f,
                0f,
                0f,
                0f,
                0f
            ),
            daysForecasts = daysForecasts?.forecasts?.map { mapForecastResponseToDayWeather(it) }
                ?: listOf()
        )
    }

    private fun mapToLocationModel(locationResponse: LocationResponse) = with(locationResponse) {
        LocationModel(
            city = city ?: "",
            region = region ?: "",
            country = country ?: "",
            latitude = latitude ?: 0f,
            longitude = longitude ?: 0f,
            timeZone = timeZone ?: "",
            time = time ?: "",
        )
    }

    private fun mapToWeatherModel(weatherResponse: WeatherInfoResponse) = with(weatherResponse) {
        WeatherModel(
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

    private fun mapToHourModel(hourResponse: HourResponse): HourModel {
        with(hourResponse) {
            val weather =
                WeatherModel(
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
            val correctTime = if (time != null) getModifiableTime(time) else ""
            return HourModel(time = correctTime, weather = weather)
        }
    }

    private fun mapForecastResponseToDayWeather(response: ForecastDayResponse): DayWeather {
        var commonDate = response.date ?: ""
        commonDate = with(commonDate) {
            substring(8, 10) + "." + substring(5, 7) + "." + substring(0, 4)
        }

        return with(response) {
            DayWeather(
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
                hourWeathers = hours?.map { mapToHourModel(it) } ?: listOf()
            )
        }
    }

    private fun getModifiableTime(timeStr: String): String {
        var i = 0
        while (timeStr[i] != ' ') i++
        return "  " + timeStr.substring(++i, timeStr.length) + "   "
    }
}