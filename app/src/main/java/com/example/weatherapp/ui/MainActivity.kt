package com.example.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.weatherapp.MapsFragment
import com.example.weatherapp.MapsFragmentDirections
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.di.GeneralComponent
import com.example.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    lateinit var generalComponent: GeneralComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        generalComponent = (application as WeatherApp).appComponent.weatherComponent().create()
        generalComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//
//            when (item.itemId) {
//                R.id.currentWeatherFragment -> {
//                    val action = MapsFragmentDirections.actionMapsFragmentToCurrentWeatherFragment()
//                    findNavController(R.id.hostFragment).navigate(action)
//                }
//                R.id.mapsFragment -> {
//
//                }
//            }
//
//            true
//        }

//        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController(R.id.hostFragment).popBackStack()
//            }
//        })
    }
}