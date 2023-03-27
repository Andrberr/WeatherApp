package com.example.weatherapp.data.di

import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.added_cities.AddedCitiesFragment
import com.example.weatherapp.ui.cities.SearchFragment
import com.example.weatherapp.ui.current_weather.CurrentWeatherFragment
import com.example.weatherapp.ui.future_weather.FutureWeatherFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface GeneralComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): GeneralComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CurrentWeatherFragment)
    fun inject(fragment: FutureWeatherFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: AddedCitiesFragment)
}