package com.example.weatherapp.ui.cities

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.CityLayoutBinding

class CitiesViewHolder(
    private val binding: CityLayoutBinding,
    private val itemClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(city: String) {
        binding.textView.text = city
        itemView.setOnClickListener {
            itemClick.invoke(city)
        }
    }
}