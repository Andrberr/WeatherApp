package com.example.weatherapp.ui.future_weather_custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FutureForecastLayoutBinding
import com.example.weatherapp.databinding.FutureWeatherLayoutBinding
import com.example.weatherapp.domain.models.DayWeather

class FutureForecastAdapter : RecyclerView.Adapter<FutureForecastViewHolder>() {

    private val forecasts = mutableListOf<DayWeather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureForecastViewHolder {
        val binding =
            FutureForecastLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FutureForecastViewHolder(binding)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: FutureForecastViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    fun setWeather(list: List<DayWeather>) {
        forecasts.clear()
        forecasts.addAll(list)
        notifyDataSetChanged()
    }
}
