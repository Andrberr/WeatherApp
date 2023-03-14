package com.example.weatherapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.data.di.WeatherComponent
import com.example.weatherapp.data.di.vm_factory.ViewModelFactory
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.domain.models.DayWeather
import com.example.weatherapp.ui.future_weather_main.FutureWeatherAdapter
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val vm: WeatherViewModel by viewModels { factory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).weatherComponent.inject(this)
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
        var forecasts: List<DayWeather>
        val adapter = FutureWeatherAdapter()
        binding.sevDayLayout.findViewById<RecyclerView>(R.id.sevDayForecastRecycler).apply {
            this.adapter = adapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        vm.weatherLiveData.observe(viewLifecycleOwner) {
            binding.cityView.text = it.location.city
            binding.tempCView.text = it.currentWeather.tempC.toString()
            binding.tempFView.text = it.currentWeather.tempF.toString()
            forecasts = it.daysForecasts
            adapter.setWeather(forecasts)
        }
        vm.getWeatherInfo()

        binding.sevDayLayout.findViewById<Button>(R.id.weekForecastButton).setOnClickListener {
            val action = CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToFutureWeatherFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}