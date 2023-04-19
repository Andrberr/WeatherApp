package com.example.weatherapp.ui.day_weather

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.DayWeatherFieldLayoutBinding

class DayWeatherViewHolder(private val binding: DayWeatherFieldLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(dayWeatherElem: DayWeatherElem) {
        binding.field.text = dayWeatherElem.field
        binding.fieldValue.text = dayWeatherElem.fieldValue
    }
}