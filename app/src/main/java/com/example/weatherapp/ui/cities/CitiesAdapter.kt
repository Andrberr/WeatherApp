package com.example.weatherapp.ui.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.CityLayoutBinding

class CitiesAdapter(private val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<CitiesViewHolder>() {

    private val cities = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val binding =
            CityLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitiesViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    fun setCities(list: List<String>) {
        cities.clear()
        cities.addAll(list)
        notifyDataSetChanged()
    }
}