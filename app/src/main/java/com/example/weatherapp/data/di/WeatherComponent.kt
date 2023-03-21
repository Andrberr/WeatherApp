package com.example.weatherapp.data.di

import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.current_weather.CurrentWeatherFragment
import com.example.weatherapp.ui.future_weather.FutureWeatherFragment
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