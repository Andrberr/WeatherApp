package com.example.weatherapp.ui.current_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.CurrentWeatherLayoutBinding
import com.example.weatherapp.domain.models.DayWeather

class CurrentWeatherAdapter : RecyclerView.Adapter<CurrentWeatherViewHolder>() {

    private val forecasts = mutableListOf<DayWeather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentWeatherViewHolder {
        val binding =
            CurrentWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrentWeatherViewHolder(binding)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: CurrentWeatherViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    fun setWeather(list: List<DayWeather>) {
        forecasts.clear()
        forecasts.addAll(list)
        notifyDataSetChanged()
    }
}
