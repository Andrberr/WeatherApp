package com.example.weatherapp.data.sources

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class UserCitySource @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun getUserCity(): String = prefs.getString(TOKEN_KEY, EMPTY_STRING).orEmpty()

    fun setUserCity(city: String) = prefs.edit {
        putString(TOKEN_KEY, city)
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val TOKEN_KEY = "TOKEN_KEY"
    }
}