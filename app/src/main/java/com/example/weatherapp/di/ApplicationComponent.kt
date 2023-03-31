package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.di.modules.*
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