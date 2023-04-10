package com.example.data.models

import com.squareup.moshi.Json

data class LocationResponse(
    @Json(name="name") val city: String? = null,
    @Json(name="region") val region: String? = null,
    @Json(name="country") val country: String? = null,
    @Json(name="lat") val latitude: Float? = null,
    @Json(name="lon") val longitude: Float? = null,
    @Json(name="tz_id") val timeZone: String? = null,
    @Json(name="localtime") val time: String? = null,
)