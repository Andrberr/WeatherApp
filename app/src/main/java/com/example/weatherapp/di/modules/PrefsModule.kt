package com.example.weatherapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class PrefsModule {
    @Provides
    fun providePrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREFS_KEY = "prefs_key"
    }
}