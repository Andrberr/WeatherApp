package com.example.weatherapp.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.SevDayForecastLayoutBinding
import com.example.weatherapp.domain.models.DayWeather
import com.bumptech.glide.request.target.Target;


class WeatherViewHolder(
    private val binding: SevDayForecastLayoutBinding
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