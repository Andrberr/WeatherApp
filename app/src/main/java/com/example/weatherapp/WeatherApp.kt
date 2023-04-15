package com.example.weatherapp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.core.WeatherWorkerFactory
import com.example.weatherapp.di.ApplicationComponent
import com.example.weatherapp.di.DaggerApplicationComponent
import javax.inject.Inject

class WeatherApp : Application() {
    @Inject
    lateinit var workerFactory: WeatherWorkerFactory

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        appComponent.inject(this)
        super.onCreate()
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(workerFactory).build()
        )
    }
}