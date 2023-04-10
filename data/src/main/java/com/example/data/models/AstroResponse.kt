package com.example.data.models

import com.squareup.moshi.Json

data class AstroResponse(
    @Json(name = "sunrise") val sunrise: String? = null,
    @Json(name = "sunset") val sunset: String? = null,
    @Json(name = "moonrise") val moonrise: String? = null,
    @Json(name = "moonset") val moonset: String? = null,
    @Json(name = "moon_phase") val moonPhase: String? = null,
    @Json(name = "moon_illumination") val moonIllumination: Float? = null,
)
