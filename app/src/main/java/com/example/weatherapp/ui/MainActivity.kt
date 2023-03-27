package com.example.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.data.di.GeneralComponent
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.current_weather.CurrentWeatherFragment
import com.example.weatherapp.ui.current_weather.CurrentWeatherFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var generalComponent: GeneralComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        generalComponent = (application as WeatherApp).appComponent.weatherComponent().create()
        generalComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController(R.id.hostFragment).popBackStack()
//            }
//        })
    }
}