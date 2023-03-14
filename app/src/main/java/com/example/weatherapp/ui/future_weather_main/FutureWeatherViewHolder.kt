package com.example.weatherapp.ui.future_weather_main

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.domain.models.DayWeather
import com.bumptech.glide.request.target.Target;
import com.example.weatherapp.databinding.FutureWeatherLayoutBinding


class FutureWeatherViewHolder(
    private val binding: FutureWeatherLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: DayWeather){
        Glide.with(itemView.context)
            .load("https:${weather.icon}")
            .override(Target.SIZE_ORIGINAL)
            .into(binding.weatherImage)
        binding.dayView.text = "\t${weather.date}"
        binding.weatherView.text = "\t${weather.textDescription}"
        binding.tempView.text = "\t${weather.avgTempC}°C/${weather.avgTempF}°F"
    }
}