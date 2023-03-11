package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.di.vm_factory.ViewModelFactory
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.ui.WeatherAdapter
import com.example.weatherapp.ui.WeatherViewModel
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val vm: WeatherViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.applicationContext as WeatherApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = WeatherAdapter()
        binding.sevDayLayout.findViewById<RecyclerView>(R.id.sevDayForecastRecycler).apply {
            this.adapter = adapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        vm.weatherLiveData.observe(viewLifecycleOwner) {
            binding.cityView.text = it.location.city
            binding.tempCView.text = it.currentWeather.tempC.toString()
            binding.tempFView.text = it.currentWeather.tempF.toString()
            adapter.setWeather(it.daysForecasts)
        }
        vm.getWeatherInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}