package com.example.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("name") val city: String? = null,
    @SerializedName("region") val region: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("lat") val latitude: Float? = null,
    @SerializedName("lon") val longitude: Float? = null,
    @SerializedName("tz_id") val timeZone: String? = null,
    @SerializedName("localtime") val time: String? = null,
)