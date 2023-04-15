package com.example.weatherapp.ui

import android.content.Context
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import com.example.core.ChildWorkerFactory
import com.example.data.repositories.WeatherRepositoryImpl
import com.example.domain.repositories.WeatherRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Provider

class WeatherWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val weatherRepository: WeatherRepository,
) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            val result = withContext(Dispatchers.IO) {
                weatherRepository.getWeatherInfo(true, weatherRepository.getUserCity(), "")
            }
            if (result == null)
                Result.retry()
            else {
                Result.success()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    class Factory @Inject constructor(
        private val foo: Provider<WeatherRepositoryImpl>
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return WeatherWorker(
                appContext,
                params,
                foo.get()
            )
        }
    }
}