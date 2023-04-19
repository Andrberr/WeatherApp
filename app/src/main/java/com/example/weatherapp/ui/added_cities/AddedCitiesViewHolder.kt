package com.example.weatherapp.ui.added_cities

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.AddedCityLayoutBinding
import com.example.domain.models.AddedCityInfo

class AddedCitiesViewHolder(
    private val binding: AddedCityLayoutBinding,
    private val nextClick: (String) -> Unit,
    private val deleteButtonClick: (String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("ClickableViewAccessibility")
    fun bind(cityInfo: AddedCityInfo) {
        with(cityInfo) {
            binding.cityView.text = city
            binding.tempView.text = temperature.toString()

            binding.nextButton.setOnClickListener {
                nextClick.invoke(city)
            }

            binding.deleteButton.setOnClickListener {
                deleteButtonClick.invoke(city)
            }

            var initialX = 0f
            var dX = 0f
            itemView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.deleteButton.visibility = View.GONE
                        binding.nextButton.visibility = View.VISIBLE
                        initialX = event.rawX
                        itemView.animate().translationX(0f)
                        return@setOnTouchListener true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        dX = event.rawX - initialX
                        if (dX <= -150f) {
                            binding.nextButton.visibility = View.GONE
                            binding.deleteButton.visibility = View.VISIBLE
                        } else if (dX > 0) {
                            itemView.translationX = 0f
                        } else {
                            binding.deleteButton.visibility = View.GONE
                            binding.nextButton.visibility = View.VISIBLE
                            itemView.animate().translationX(dX)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (dX >= -150f) {
                            itemView.animate().translationX(0f)
                            binding.deleteButton.visibility = View.GONE
                            binding.nextButton.visibility = View.VISIBLE
                            initialX = 0f
                            dX = 0f
                        }
                    }
                    else -> return@setOnTouchListener false
                }
                true
            }
        }
    }
}
