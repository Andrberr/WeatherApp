package com.example.data.sources

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class UserCitySource @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun getUserCity(token: String): String = prefs.getString(token, EMPTY_STRING).orEmpty()

    fun setUserCity(city: String, token: String) = prefs.edit {
        putString(token, city)
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}