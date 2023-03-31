package com.example.weatherapp.ui.added_cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.AddedCityLayoutBinding
import com.example.domain.models.AddedCityInfo

class AddedCitiesAdapter(private val itemClick: (String) -> Unit): RecyclerView.Adapter<AddedCitiesViewHolder>() {

    private val cities = mutableListOf<AddedCityInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedCitiesViewHolder {
        val binding =
            AddedCityLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddedCitiesViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: AddedCitiesViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    fun setCities(list: List<AddedCityInfo>){
        cities.clear()
        cities.addAll(list)
        notifyDataSetChanged()
    }
}