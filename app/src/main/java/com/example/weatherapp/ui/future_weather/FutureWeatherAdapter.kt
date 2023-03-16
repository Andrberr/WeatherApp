package com.example.weatherapp.ui.future_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FutureWeatherLayoutBinding
import com.example.weatherapp.domain.models.DayWeather

class FutureWeatherAdapter : RecyclerView.Adapter<FutureWeatherViewHolder>() {

    private val forecasts = mutableListOf<DayWeather>()

    private var minTemp: Float = 0f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureWeatherViewHolder {
        val binding =
            FutureWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FutureWeatherViewHolder(binding)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: FutureWeatherViewHolder, position: Int) {
        holder.bind(forecasts[position], minTemp)
    }

    fun setWeather(list: List<DayWeather>, minTemp: Float) {
        forecasts.clear()
        forecasts.addAll(list)
        this.minTemp = minTemp
        notifyDataSetChanged()
    }
}
