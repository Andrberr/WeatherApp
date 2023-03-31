package com.example.domain.models

data class LocationModel(
    val city: String,
    val region: String,
    val country: String,
    val latitude: Float,
    val longitude: Float,
    val timeZone: String,
    val time: String
)
