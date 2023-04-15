package com.example.weatherapp.di

import com.example.weatherapp.WeatherApp
import com.example.weatherapp.ui.day_weather.DayWeatherFragment
import com.example.weatherapp.ui.loading.LoadingFragment
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.added_cities.AddedCitiesFragment
import com.example.weatherapp.ui.cities.SearchFragment
import com.example.weatherapp.ui.current_weather.CurrentWeatherFragment
import com.example.weatherapp.ui.current_weather.hour_dialog.HourDialogFragment
import com.example.weatherapp.ui.future_weather.FutureWeatherFragment
import com.example.weatherapp.ui.maps.MapsFragment
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
    fun inject(fragment: HourDialogFragment)
    fun inject(fragment: DayWeatherFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: AddedCitiesFragment)
    fun inject(fragment: LoadingFragment)
    fun inject(fragment: MapsFragment)
}