package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.di.ApplicationComponent
import com.example.weatherapp.data.di.DaggerApplicationComponent

class WeatherApp : Application() {
    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}