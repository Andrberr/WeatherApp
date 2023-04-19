package com.example.weatherapp.ui.future_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.DayWeather
import com.example.weatherapp.databinding.BarChartLayoutBinding

class FutureWeatherAdapter(
    private val itemClick: (DayWeather) -> Unit
) : RecyclerView.Adapter<FutureWeatherViewHolder>() {

    private val forecasts = mutableListOf<DayWeather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureWeatherViewHolder {
        val binding =
            BarChartLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FutureWeatherViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: FutureWeatherViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    fun setWeather(list: List<DayWeather>) {
        forecasts.clear()
        forecasts.addAll(list)
        notifyDataSetChanged()
    }
}
