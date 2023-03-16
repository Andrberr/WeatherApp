package com.example.weatherapp.domain.use_cases

import com.example.weatherapp.domain.Repository
import javax.inject.Inject

class MinTemperatureUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): Float {
        val list = repository.getWeatherInfo().daysForecasts
        var min = list[0].avgTempC
        var i = 1
        while (i < list.size) {
            if (list[i].avgTempC < min) min = list[i].avgTempC
            i++
        }
        return min
    }
}