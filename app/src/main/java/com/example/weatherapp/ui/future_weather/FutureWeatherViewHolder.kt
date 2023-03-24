package com.example.weatherapp.ui.future_weather

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target;
import com.example.weatherapp.databinding.FutureWeatherLayoutBinding
import com.example.weatherapp.domain.models.DayWeather

class FutureWeatherViewHolder(
    private val binding: FutureWeatherLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: DayWeather) {
        binding.dayView.text = "\t${weather.date}"
        Glide.with(itemView.context)
            .load("https:${weather.icon}")
            .override(Target.SIZE_ORIGINAL)
            .into(binding.weatherImage)
        binding.customLine.setParams(weather.avgTempC)
    }
}