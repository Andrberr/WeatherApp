package com.example.weatherapp.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.example.core.ViewModelFactory
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.di.GeneralComponent
import com.example.weatherapp.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var generalComponent: GeneralComponent

    @Inject
    lateinit var factory: ViewModelFactory
    private val weatherViewModel: WeatherViewModel by viewModels { factory }
    private val citiesViewModel: CitiesViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        generalComponent = (application as WeatherApp).appComponent.weatherComponent().create()
        generalComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initWorker(this, this)
    }

    @SuppressLint("RestrictedApi")
    private fun initWorker(context: Context, viewLifecycleOwner: LifecycleOwner) {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true).build()

        val uploadWork =
            PeriodicWorkRequest
                .Builder(WeatherWorker::class.java, 15, TimeUnit.MINUTES)
                .setInitialDelay(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        val workManager = WorkManager.getInstance(context).enqueue(uploadWork)
        workManager.state.observe(viewLifecycleOwner) { state ->
            if (state == Operation.SUCCESS) {
                citiesViewModel.userCityLiveData.observe(this) {
                    weatherViewModel.getUpdatedWeatherInfo(it)
                }
                citiesViewModel.getUserCity()
            }
        }
    }
}