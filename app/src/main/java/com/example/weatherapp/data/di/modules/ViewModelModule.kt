package com.example.weatherapp.data.di.modules

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.di.vm_factory.ViewModelKey
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