package com.example.weatherapp.data.network

import com.example.weatherapp.data.models.cities_models.CitiesList
import retrofit2.Call
import retrofit2.http.GET

interface CitiesService {
    @GET("countries/population/cities")
    fun getCitiesResponse(): Call<CitiesList>
}