package com.example.weatherapp.ui.future_weather_custom

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.domain.models.DayWeather
import com.bumptech.glide.request.target.Target;
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FutureForecastLayoutBinding


class FutureForecastViewHolder(
    private val binding: FutureForecastLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: DayWeather){
        binding.dayView.text = "\t${weather.date}"
        Glide.with(itemView.context)
            .load("https:${weather.icon}")
            .override(Target.SIZE_ORIGINAL)
            .into(binding.weatherImage)
        binding.tempView.text = "\t${weather.avgTempC}°C"
        binding.customLine.setLineHeight(weather.avgTempC)
    }
}