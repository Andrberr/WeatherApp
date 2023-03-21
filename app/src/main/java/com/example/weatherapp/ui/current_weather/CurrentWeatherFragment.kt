package com.example.weatherapp.ui.current_weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.data.di.vm_factory.ViewModelFactory
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.current_weather.general_weather.CurrentWeatherAdapter
import com.example.weatherapp.ui.current_weather.more_weather.MoreWeatherAdapter
import com.example.weatherapp.ui.current_weather.more_weather.MoreWeatherElem
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
        val generalWeatherAdapter = CurrentWeatherAdapter()
        binding.threeDayForecastRecycler.apply {
            this.adapter = generalWeatherAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val moreWeatherAdapter = MoreWeatherAdapter()
        binding.moreInfoRecycler.apply {
            this.adapter = moreWeatherAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        vm.weatherLiveData.observe(viewLifecycleOwner) {
            binding.cityView.text = it.location.city
            generalWeatherAdapter.setWeather(it.daysForecasts.subList(0, 3))

            with(it.currentWeather) {
                binding.tempCView.text = tempC.toString()
                binding.tempFView.text = tempF.toString()
                binding.windDirView.text = windDirection
                binding.windSpeedView.text = windSpeed.toString()
                val moreWeatherList =
                    listOf(
                        MoreWeatherElem("Real feel(°C)", "$feelingC"),
                        MoreWeatherElem("Real feel(°F)", "$feelingF"),
                        MoreWeatherElem("Humidity", "$humidityPercent%"),
                        MoreWeatherElem("Cloud cover", "$cloudPercent%"),
                        MoreWeatherElem("Pressure", "$pressure" + "mbar"),
                        MoreWeatherElem("Precipitation", "$precipitationAmountHour" + "mm/h"),
                        MoreWeatherElem("Visibility", "$visibilityKm" + "km/h"),
                        MoreWeatherElem("Wind gust", "$gustWindSpeed" + "km/h")
                    )
                moreWeatherAdapter.setMoreWeather(moreWeatherList)
            }
        }
        vm.getWeatherInfo()

        binding.weekForecastButton.setOnClickListener {
            val action =
                CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToFutureWeatherFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}