package com.example.weatherapp.ui.day_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.DayWeatherFieldLayoutBinding

class DayWeatherAdapter: RecyclerView.Adapter<DayWeatherViewHolder>() {

    private val fieldsList  = mutableListOf<DayWeatherElem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWeatherViewHolder {
        val binding =
            DayWeatherFieldLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayWeatherViewHolder(binding)
    }

    override fun getItemCount(): Int  = fieldsList.size

    override fun onBindViewHolder(holder: DayWeatherViewHolder, position: Int) {
        holder.bind(fieldsList[position])
    }

    fun setDayWeather(list: List<DayWeatherElem>) {
        fieldsList.clear()
        fieldsList.addAll(list)
        notifyDataSetChanged()
    }
}