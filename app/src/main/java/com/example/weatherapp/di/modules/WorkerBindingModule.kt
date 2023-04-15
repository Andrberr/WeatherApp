package com.example.weatherapp.di.modules

import com.example.core.ChildWorkerFactory
import com.example.core.WorkerKey
import com.example.weatherapp.ui.WeatherWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(WeatherWorker::class)
    fun bindWeatherWorker(factory: WeatherWorker.Factory): ChildWorkerFactory
}