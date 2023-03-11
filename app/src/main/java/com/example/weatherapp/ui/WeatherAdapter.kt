package com.example.weatherapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.SevDayForecastLayoutBinding
import com.example.weatherapp.domain.models.DayWeather

class WeatherAdapter : RecyclerView.Adapter<WeatherViewHolder>() {

    private val forecasts = mutableListOf<DayWeather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding =
            SevDayForecastLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    fun setWeather(list: List<DayWeather>) {
        forecasts.clear()
        forecasts.addAll(list)
        notifyDataSetChanged()
    }
}
