package com.example.weatherapp.ui.current_weather.general_weather

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target;
import com.example.weatherapp.databinding.CurrentWeatherLayoutBinding
import com.example.weatherapp.domain.models.DayWeather

class CurrentWeatherViewHolder(
    private val binding: CurrentWeatherLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: DayWeather){
        Glide.with(itemView.context)
            .load("https:${weather.icon}")
            .override(Target.SIZE_ORIGINAL)
            .into(binding.weatherImage)
        binding.dayView.text = "\t${weather.date}"
        binding.weatherView.text = "\t${weather.textDescription}\n"
        binding.tempView.text = "\t${weather.avgTempC}°C/${weather.avgTempF}°F"
    }
}