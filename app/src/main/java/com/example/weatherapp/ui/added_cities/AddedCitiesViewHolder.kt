package com.example.weatherapp.ui.added_cities

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.AddedCityLayoutBinding
import com.example.domain.models.AddedCityInfo

class AddedCitiesViewHolder(
    private val binding: AddedCityLayoutBinding,
    private val itemClick: (String) -> Unit,
    private val deleteButtonClick: (String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("ClickableViewAccessibility")
    fun bind(cityInfo: AddedCityInfo) {
        with(cityInfo) {
            binding.cityView.text = "$city  "
            binding.tempView.text = temperature.toString()

            binding.nextButton.setOnClickListener {
                itemClick.invoke(city)
            }

            binding.deleteButton.setOnClickListener {
                deleteButtonClick.invoke(city)
            }

            var initialX = 0f
            var dX = 0f
            itemView.setOnTouchListener { v, event ->
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
                            // показать картинку
                            binding.nextButton.visibility = View.GONE
                            binding.deleteButton.visibility = View.VISIBLE
                            // itemView.translationX = -50f // сдвинуть элемент
                        } else if (dX > 0) {
                            // ограничить сдвиг элемента только вправо
                            itemView.translationX = 0f
                        } else {
                            // скрыть картинку
                            binding.deleteButton.visibility = View.GONE
                            binding.nextButton.visibility = View.VISIBLE
                            itemView.animate().translationX(dX) // сдвинуть элемент
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        // элемент не удаляется
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
