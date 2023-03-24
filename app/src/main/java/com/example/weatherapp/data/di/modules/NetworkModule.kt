package com.example.weatherapp.data.di.modules

import com.example.weatherapp.data.di.CitiesQualifier
import com.example.weatherapp.data.di.WeatherQualifier
import com.example.weatherapp.data.network.CitiesService
import com.example.weatherapp.data.network.WeatherService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    @WeatherQualifier
    fun getWeatherRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    @CitiesQualifier
    fun getCitiesRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://countriesnow.space/api/v0.1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun getWeatherService(@WeatherQualifier retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

    @Provides
    @Singleton
    fun getCitiesService(@CitiesQualifier retrofit: Retrofit): CitiesService  = retrofit.create(CitiesService::class.java)

    @Provides
    @Singleton
    fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return OkHttpClient.Builder().addInterceptor(interceptor).build();
    }
}