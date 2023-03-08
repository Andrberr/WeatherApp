package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.models.WeatherResponse
import com.example.weatherapp.domain.models.WeatherInfo
import javax.inject.Inject

class WeatherMapper @Inject constructor() {
    operator fun invoke(response: WeatherResponse) = with(response) {
        WeatherInfo(
            city = locationInfo?.city ?: "",
            region = locationInfo?.region ?: "",
            country = locationInfo?.country ?: "",
            latitude = locationInfo?.latitude ?: 0f,
            longitude = locationInfo?.longitude ?: 0f,
            timeZone = locationInfo?.timeZone ?: "",
            time = locationInfo?.time ?: "",
            tempC = weather?.tempC ?: 0f,
            tempF = weather?.tempF ?: 0f,
            isDay = weather?.isDay ?: 0,
            textDescription = weather?.weatherCondition?.textDescription ?: "",
            icon = weather?.weatherCondition?.icon ?: "",
            windSpeed = weather?.windSpeed ?: 0f,
            windDegree = weather?.windDegree ?: 0f,
            windDirection = weather?.windDirection ?: "",
            pressure = weather?.pressure ?: 0f,
            precipitationAmountHour = weather?.precipitationAmountHour ?: 0f,
            humidityPercent = weather?.humidityPercent ?: 0f,
            cloudPercent = weather?.cloudPercent ?: 0f,
            feelingC = weather?.feelingC ?: 0f,
            feelingF = weather?.feelingF ?: 0f,
            visibilityKm = weather?.visibilityKm ?: 0f,
            ultravioletInd = weather?.ultravioletInd ?: 0f,
            gustWindSpeed = weather?.gustWindSpeed ?: 0f,
        )
    }
}