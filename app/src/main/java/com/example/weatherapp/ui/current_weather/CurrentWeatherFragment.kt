package com.example.weatherapp.ui.current_weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.ui.MainActivity
import com.example.core.ViewModelFactory
import com.example.domain.models.HourModel
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.current_weather.bar_chart.HourWeatherAdapter
import com.example.weatherapp.ui.current_weather.general_weather.CurrentWeatherAdapter
import com.example.weatherapp.ui.current_weather.more_weather.MoreWeatherAdapter
import com.example.weatherapp.ui.current_weather.more_weather.MoreWeatherElem
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val vm: GeneralViewModel by viewModels { factory }

    private val args: CurrentWeatherFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generalComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        setInvisibleParams()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val generalWeatherAdapter = CurrentWeatherAdapter()
        binding.threeDayForecastRecycler.apply {
            adapter = generalWeatherAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val moreWeatherAdapter = MoreWeatherAdapter()
        binding.moreInfoRecycler.apply {
            adapter = moreWeatherAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val barChartAdapter = HourWeatherAdapter()
        binding.forecastRecycler.apply {
            adapter = barChartAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        vm.weatherLiveData.observe(viewLifecycleOwner) {
            if ((it.location.city != args.prevCity) || args.isSame) {
                binding.cityView.text = it.location.city
                generalWeatherAdapter.setWeather(it.daysForecasts.subList(0, 3))

                with(it.currentWeather) {
                    setVisibleParams()

                    binding.tempCView.text = tempC.toString()
                    binding.tempFView.text = tempF.toString()
                    binding.windDirView.text = windDirection
                    binding.windSpeedView.text = windSpeed.toString()
                    val moreWeatherList =
                        listOf(
                            MoreWeatherElem("Real feel(°C)", getModifiableFloat(feelingC)),
                            MoreWeatherElem("Real feel(°F)", getModifiableFloat(feelingF)),
                            MoreWeatherElem("Humidity", "${getModifiableFloat(humidityPercent)}%"),
                            MoreWeatherElem("Cloud cover", "${getModifiableFloat(cloudPercent)}%"),
                            MoreWeatherElem("Pressure", getModifiableFloat(pressure) + "mbar"),
                            MoreWeatherElem(
                                "Precipitation",
                                getModifiableFloat(precipitationAmountHour) + "mm/h"
                            ),
                            MoreWeatherElem(
                                "Visibility",
                                getModifiableFloat(visibilityKm) + "km/h"
                            ),
                            MoreWeatherElem("Wind gust", getModifiableFloat(gustWindSpeed) + "km/h")
                        )
                    moreWeatherAdapter.setMoreWeather(moreWeatherList)
                }

                barChartAdapter.setWeather(it.daysForecasts[0].hourWeathers)
            }
        }

        if (args.needUpdate) {
            vm.userCityLiveData.observe(viewLifecycleOwner) {
                vm.getWeatherInfo(it)
            }
            vm.getUserCity()
        }

        binding.weekForecastButton.setOnClickListener {
            val action =
                CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToFutureWeatherFragment()
            findNavController().navigate(action)
        }

        binding.addButton.setOnClickListener {
            val action =
                CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToSearchFragment()
            findNavController().navigate(action)
        }

        binding.citiesWeatherButton.setOnClickListener {
            val action =
                CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToAddedCitiesFragment()
            findNavController().navigate(action)
        }

    }

    private fun setInvisibleParams() {
        with(binding) {
            gradC.visibility = View.INVISIBLE
            gradF.visibility = View.INVISIBLE
            threeDayLayout.visibility = View.INVISIBLE
            windLayout.visibility = View.INVISIBLE
            delimeterView.visibility = View.INVISIBLE
            delimeterView2.visibility = View.INVISIBLE
            citiesWeatherButton.visibility = View.INVISIBLE
            addButton.visibility = View.INVISIBLE
        }
    }

    private fun setVisibleParams() {
        with(binding) {
            gradC.visibility = View.VISIBLE
            gradF.visibility = View.VISIBLE
            citiesWeatherButton.visibility = View.VISIBLE
            addButton.visibility = View.VISIBLE
            threeDayLayout.visibility = View.VISIBLE
            windLayout.visibility = View.VISIBLE
            delimeterView.visibility = View.VISIBLE
            delimeterView2.visibility = View.VISIBLE
            lottieView.visibility = View.GONE
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