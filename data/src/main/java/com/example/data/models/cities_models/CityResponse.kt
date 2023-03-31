package com.example.data.models.cities_models

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("city") val city: String
)