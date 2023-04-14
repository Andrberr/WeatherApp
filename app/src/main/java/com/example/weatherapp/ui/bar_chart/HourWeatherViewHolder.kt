package com.example.weatherapp.ui.bar_chart

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target;
import com.example.domain.models.HourModel
import com.example.domain.models.WeatherModel
import com.example.weatherapp.R
import com.example.weatherapp.databinding.BarChartLayoutBinding

class HourWeatherViewHolder(
    private val binding: BarChartLayoutBinding,
    private val nextClick: (WeatherModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(weather: HourModel) {
        binding.dayView.text = "\t${weather.time}"
        Glide.with(itemView.context)
            .load("https:${weather.weather.icon}")
            .override(Target.SIZE_ORIGINAL)
            .into(binding.weatherImage)
        binding.customLine.setParams(weather.weather.tempC, itemView.context.getColor(R.color.chart_color))

        itemView.setOnClickListener {
            nextClick.invoke(weather.weather)
        }
    }
}