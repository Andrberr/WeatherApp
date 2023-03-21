package com.example.weatherapp.ui.current_weather.more_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.MoreInfoLayoutBinding

class MoreWeatherAdapter: RecyclerView.Adapter<MoreWeatherViewHolder>() {

    private val moreList  = mutableListOf<MoreWeatherElem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreWeatherViewHolder {
        val binding =
            MoreInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreWeatherViewHolder(binding)
    }

    override fun getItemCount(): Int  = moreList.size

    override fun onBindViewHolder(holder: MoreWeatherViewHolder, position: Int) {
       holder.bind(moreList[position])
    }

    fun setMoreWeather(list: List<MoreWeatherElem>) {
        moreList.clear()
        moreList.addAll(list)
        notifyDataSetChanged()
    }
}