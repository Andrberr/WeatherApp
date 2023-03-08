package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.example.weatherapp.data.di.vm_factory.ViewModelFactory
import com.example.weatherapp.ui.WeatherViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val vm: WeatherViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.weatherLiveData.observe(this){
            findViewById<TextView>(R.id.textView).text = it.textDescription
        }
        vm.getFlights()
    }
}