package com.example.weatherapp.data.network

import com.example.weatherapp.data.models.weather_models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherService {
    @Headers("key: 2cfcdfa32bf24d06a21195037232203")
    @GET("forecast.json")
    fun getWeatherResponse(
        @Query("q") city: String,
        @Query("days") days: Int
    ): Call<WeatherResponse>
}