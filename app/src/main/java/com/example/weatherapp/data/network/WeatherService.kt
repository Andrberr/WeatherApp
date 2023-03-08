package com.example.weatherapp.data.network

import com.example.weatherapp.data.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current.json")
    fun getWeatherResponse(
        @Query("key") token: String,
        @Query("q") city: String,
    ): Call<WeatherResponse>
}