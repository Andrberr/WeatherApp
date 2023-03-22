package com.example.weatherapp.data.models.cities_models

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("city") val city: String
)