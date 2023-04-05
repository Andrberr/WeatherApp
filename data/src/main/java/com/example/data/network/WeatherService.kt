package com.example.data.network

import com.example.data.models.weather_models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherService {
    @Headers("key: 28634b21cab64cd7be7180201230504")
    @GET("forecast.json")
    fun getWeatherResponse(
        @Query("q") city: String,
        @Query("days") days: Int
    ): Call<WeatherResponse>
}