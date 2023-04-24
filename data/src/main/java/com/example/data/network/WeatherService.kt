package com.example.data.network

import com.example.data.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherService {
    @Headers("key: 1548969297a04400a67111456232404")
    @GET("forecast.json")
    suspend fun getWeatherResponse(
        @Query("q") city: String,
        @Query("days") days: Int
    ): WeatherResponse
}