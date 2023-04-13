package com.example.weatherapp.ui.day_weather

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.core.ViewModelFactory
import com.example.domain.models.WeatherModel
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDayWeatherBinding
import com.example.weatherapp.databinding.HourDialogLayoutBinding
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.bar_chart.HourWeatherAdapter
import com.example.weatherapp.ui.current_weather.hour_dialog.HourDialogFragment
import com.example.weatherapp.ui.current_weather.more_weather.MoreWeatherElem
import com.example.weatherapp.ui.future_weather.FutureWeatherFragmentDirections
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
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val action =
                        DayWeatherFragmentDirections.actionDayWeatherFragmentToFutureWeatherFragment()
                    findNavController().navigate(action)
                }
            })

        _binding = FragmentDayWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val dayWeatherAdapter = DayWeatherAdapter()
        binding.recycler.apply {
            adapter = dayWeatherAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val nextClick: (WeatherModel) -> Unit = {
            viewModel.getHourWeatherInfo(it)
            showHourDialog()
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

    private fun showHourDialog() {
        val builder = AlertDialog.Builder(requireContext())

        val dialogLayout = HourDialogLayoutBinding.inflate(layoutInflater, null, false)
        builder.setView(dialogLayout.root)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.background_constraint)

        viewModel.hourWeatherLiveData.observe(viewLifecycleOwner) {
            with(dialogLayout) {
                cloudPercent.text = getModifiableFloat(it.cloudPercent)
                feelingC.text = getModifiableFloat(it.feelingC)
                feelingF.text = getModifiableFloat(it.feelingF)
                gustWindSpeed.text = getModifiableFloat(it.gustWindSpeed)
                humidityPercent.text = getModifiableFloat(it.humidityPercent)
                precipitationAmountHour.text = getModifiableFloat(it.precipitationAmountHour)
                pressure.text = getModifiableFloat(it.pressure)
                visibilityKm.text = getModifiableFloat(it.visibilityKm)
                windSpeed.text = getModifiableFloat(it.windSpeed)
                Glide.with(this@DayWeatherFragment)
                    .load("https:" + it.icon)
                    .into(iconView)

                windDirView.text = it.windDirection
            }
        }

        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}