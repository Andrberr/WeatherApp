package com.example.weatherapp.data.models.cities_models

import com.google.gson.annotations.SerializedName

data class CitiesList(
    @SerializedName("data") val citiesList: List<City>
)
