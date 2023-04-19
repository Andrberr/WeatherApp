package com.example.weatherapp.ui.current_weather.more_weather

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.MoreInfoLayoutBinding

class MoreWeatherViewHolder(private val binding: MoreInfoLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(moreWeather: MoreWeatherElem) {
        binding.field.text = moreWeather.field
        binding.fieldValue.text = moreWeather.fieldValue
    }
}