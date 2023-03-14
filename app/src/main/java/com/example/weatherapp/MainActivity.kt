package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.weatherapp.data.di.WeatherComponent
import com.example.weatherapp.data.di.vm_factory.ViewModelFactory
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.WeatherViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var weatherComponent: WeatherComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        weatherComponent = (application as WeatherApp).appComponent.weatherComponent().create()
        weatherComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}