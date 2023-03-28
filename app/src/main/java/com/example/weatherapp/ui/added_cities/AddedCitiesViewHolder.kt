package com.example.weatherapp.ui.added_cities

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.AddedCityLayoutBinding
import com.example.weatherapp.domain.models.AddedCityInfo

class AddedCitiesViewHolder(
    private val binding: AddedCityLayoutBinding,
    private val itemClick: (String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(cityInfo: AddedCityInfo) {
        with(cityInfo) {
            binding.cityView.text = city
            binding.tempView.text = temperature.toString()
            itemView.setOnClickListener {
                itemClick.invoke(city)
            }
        }
    }
}