package com.example.weatherapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.core.ViewModelKey
import com.example.weatherapp.ui.GeneralViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GeneralViewModel::class)
    fun bindWeatherViewModel(generalViewModel: GeneralViewModel): ViewModel
}