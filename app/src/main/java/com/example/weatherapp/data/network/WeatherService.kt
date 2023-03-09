package com.example.weatherapp.data.network

import com.example.weatherapp.data.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherService {
    @Headers("key: 9c898f67b7b94bd39fe203747230703")
    @GET("forecast.json")
    fun getWeatherResponse(
        @Query("q") city: String,
        @Query("days") days: Int
    ): Call<WeatherResponse>
}