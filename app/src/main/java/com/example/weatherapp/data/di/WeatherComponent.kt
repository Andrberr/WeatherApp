package com.example.weatherapp.data.di

import com.example.weatherapp.MainActivity
import com.example.weatherapp.ui.CurrentWeatherFragment
import com.example.weatherapp.ui.FutureWeatherFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface WeatherComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): WeatherComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CurrentWeatherFragment)
    fun inject(fragment: FutureWeatherFragment)
}