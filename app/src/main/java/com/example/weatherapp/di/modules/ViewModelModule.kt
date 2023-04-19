package com.example.weatherapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.core.annotations.ViewModelKey
import com.example.weatherapp.ui.CitiesViewModel
import com.example.weatherapp.ui.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    fun bindWeatherViewModel(weatherViewModel: WeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CitiesViewModel::class)
    fun bindCitiesViewModel(citiesViewModel: CitiesViewModel): ViewModel
}