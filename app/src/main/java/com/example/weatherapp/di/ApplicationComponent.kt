package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.di.modules.*
import com.example.weatherapp.ui.added_cities.AddedCitiesFragment
import com.example.weatherapp.ui.cities.SearchFragment
import com.example.weatherapp.ui.loading.LoadingFragment
import com.example.weatherapp.ui.maps.MapsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, ViewModelModule::class, AppSubComponents::class, DataBaseModule::class, NetworkModule::class, PrefsModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun weatherComponent(): GeneralComponent.Factory
}