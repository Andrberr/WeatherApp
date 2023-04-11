package com.example.data.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class WeatherConditionResponse(
    @Json(name="text") val textDescription: String? = null,
    @Json(name="icon") val icon: String? = null
)