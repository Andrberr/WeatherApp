package com.example.weatherapp.data.di.modules

import com.example.weatherapp.data.RepositoryImpl
import com.example.weatherapp.domain.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun getRepository(impl: RepositoryImpl): Repository
}