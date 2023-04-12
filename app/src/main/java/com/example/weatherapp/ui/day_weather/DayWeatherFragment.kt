package com.example.weatherapp.ui.day_weather

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.core.ViewModelFactory
import com.example.domain.models.WeatherModel
import com.example.weatherapp.databinding.FragmentDayWeatherBinding
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.bar_chart.HourWeatherAdapter
import com.example.weatherapp.ui.current_weather.hour_dialog.HourDialogFragment
import com.example.weatherapp.ui.current_weather.more_weather.MoreWeatherElem
import javax.inject.Inject

class DayWeatherFragment : Fragment() {

    private var _binding: FragmentDayWeatherBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: GeneralViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generalComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.backButton.setOnClickListener {
            val action =
                DayWeatherFragmentDirections.actionDayWeatherFragmentToFutureWeatherFragment()
            findNavController().navigate(action)
        }

        val dayWeatherAdapter = DayWeatherAdapter()
        binding.recycler.apply {
            adapter = dayWeatherAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val nextClick: (WeatherModel) -> Unit = {
            viewModel.getHourWeatherInfo(it)
//            val dialogFragment = HourDialogFragment()
//            dialogFragment.show(childFragmentManager, "hour_dialog")
            val action = DayWeatherFragmentDirections.actionDayWeatherFragmentToHourDialogFragment()
            findNavController().navigate(action)
        }

        val barChartAdapter = HourWeatherAdapter(nextClick)
        binding.forecastRecycler.apply {
            adapter = barChartAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.dayWeatherLiveData.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load("https:${it.icon}")
                .override(Target.SIZE_ORIGINAL)
                .into(binding.iconView)

            binding.textDescriptionView.text = it.textDescription

            val dayWeatherList = with(it) {
                listOf(
                    DayWeatherElem("Average temperature(°C)", getModifiableFloat(avgTempC) + " "),
                    DayWeatherElem("Average temperature(°F)", getModifiableFloat(avgTempF) + " "),
                    DayWeatherElem("Max temperature(°C)", getModifiableFloat(maxTempC) + " "),
                    DayWeatherElem("Max temperature(°F)", getModifiableFloat(maxTempF) + " "),
                    DayWeatherElem("Min temperature(°C)", getModifiableFloat(minTempC) + " "),
                    DayWeatherElem("Min temperature(°F)", getModifiableFloat(minTempF) + " "),
                    DayWeatherElem("Rain chance", getModifiableFloat(rainChance) + "% "),
                    DayWeatherElem("Snow chance", getModifiableFloat(snowChance) + "% "),
                    DayWeatherElem("Humidity", "${getModifiableFloat(avgHumidity)}% "),
                    DayWeatherElem(
                        "Total precipitation",
                        getModifiableFloat(totalPrecipitation) + "mm/h "
                    ),
                    DayWeatherElem(
                        "Visibility",
                        getModifiableFloat(avgVisibility) + "km/h "
                    ),
                    DayWeatherElem("Max wind speed", getModifiableFloat(maxWindSpeed) + "km/h "),
                    DayWeatherElem("UV Index", getModifiableFloat(ultravioletInd) + " "),
                    DayWeatherElem("Moon phase", "$moonPhase "),
                    DayWeatherElem("Moon illumination", getModifiableFloat(moonIllumination) + "% ")
                )
            }
            dayWeatherAdapter.setDayWeather(dayWeatherList)

            binding.sunriseView.text = "Sunrise:\n${it.sunrise} "
            binding.sunsetView.text = "Sunset:\n${it.sunset} "
            binding.moonriseView.text = "Moonrise:\n${it.moonrise} "
            binding.moonsetView.text = "Moonset:\n${it.moonset} "
            barChartAdapter.setWeather(it.hourWeathers)
        }

    }

    private fun getModifiableFloat(value: Float): String {
        val str = value.toString().split(".")
        if (str[1].toFloat() != 0f) return value.toString()
        return str[0]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}