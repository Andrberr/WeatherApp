package com.example.weatherapp.ui.future_weather

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target;
import com.example.domain.models.DayWeather
import com.example.weatherapp.databinding.BarChartLayoutBinding

class FutureWeatherViewHolder(
    private val binding: BarChartLayoutBinding,
    private val itemClick: (DayWeather) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: DayWeather) {
        binding.dayView.text = "\t${weather.date}"
        Glide.with(itemView.context)
            .load("https:${weather.icon}")
            .override(Target.SIZE_ORIGINAL)
            .into(binding.weatherImage)
        binding.customLine.setParams(weather.avgTempC, Color.BLUE)

        itemView.setOnClickListener{
            itemClick.invoke(weather)
        }
    }
}