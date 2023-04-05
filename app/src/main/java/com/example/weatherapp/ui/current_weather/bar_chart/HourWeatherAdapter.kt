package com.example.weatherapp.ui.current_weather.bar_chart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.HourModel
import com.example.weatherapp.databinding.BarChartLayoutBinding

class HourWeatherAdapter(private val nextClick: () -> Unit) : RecyclerView.Adapter<HourWeatherViewHolder>() {

    private val forecasts = mutableListOf<HourModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourWeatherViewHolder {
        val binding =
            BarChartLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourWeatherViewHolder(binding, nextClick)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: HourWeatherViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    fun setWeather(list: List<HourModel>) {
        forecasts.clear()
        forecasts.addAll(list)
        notifyDataSetChanged()
    }
}
