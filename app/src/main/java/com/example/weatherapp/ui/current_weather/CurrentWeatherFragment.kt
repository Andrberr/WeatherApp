package com.example.weatherapp.ui.current_weather

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.core.factories.ViewModelFactory
import com.example.domain.models.HourModel
import com.example.domain.models.LocationModel
import com.example.domain.models.WeatherInfo
import com.example.domain.models.WeatherModel
import com.example.weatherapp.R
import com.example.weatherapp.databinding.CityDialogLayoutBinding
import com.example.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.example.weatherapp.databinding.HourDialogLayoutBinding
import com.example.weatherapp.databinding.OptionsDialogLayoutBinding
import com.example.weatherapp.ui.CitiesViewModel
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.bar_chart.HourWeatherAdapter
import com.example.weatherapp.ui.current_weather.general_weather.CurrentWeatherAdapter
import com.example.weatherapp.ui.current_weather.more_weather.MoreWeatherAdapter
import com.example.weatherapp.ui.current_weather.more_weather.MoreWeatherElem
import java.util.*
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val weatherViewModel: WeatherViewModel by viewModels { factory }
    private val citiesViewModel: CitiesViewModel by viewModels { factory }

    private val args: CurrentWeatherFragmentArgs by navArgs()

    private lateinit var locationModel: LocationModel
    private val generalWeatherAdapter = CurrentWeatherAdapter()
    private val moreWeatherAdapter = MoreWeatherAdapter()
    private lateinit var barChartAdapter: HourWeatherAdapter

    private var isPaused = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generalComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            })

        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        setInvisibleParams()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initRecyclers()

        var wasUpdated = true
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            wasUpdated = !wasUpdated

            if (wasUpdated || args.update) {
                if (it == null) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_found),
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToSearchFragment()
                } else {
                    citiesViewModel.setRememberCity("")
                    updateData(it)
                }
            }
        }

        var getRemembered = false
        citiesViewModel.rememberCityLiveData.observe(viewLifecycleOwner) {
            if (args.backFromSearch && it.isNotEmpty()) {
                getRemembered = true
                weatherViewModel.getWeatherFromDataBase(it)
            }
        }
        citiesViewModel.getRememberCity()

        citiesViewModel.userCityLiveData.observe(viewLifecycleOwner) {
            if (args.needUpdate) weatherViewModel.getWeatherInfo(it, "")
            else if (!getRemembered) weatherViewModel.getWeatherFromDataBase(it)
        }
        citiesViewModel.getUserCity()

        setButtonsClickListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun updateData(weather: WeatherInfo) {
        locationModel = weather.location

        binding.cityView.text = weather.location.city
        generalWeatherAdapter.setWeather(weather.daysForecasts.subList(0, 3))

        with(weather.currentWeather) {
            setVisibleParams()

            binding.tempCView.text = tempC.toString()
            binding.tempFView.text = tempF.toString()
            binding.windDirView.text = windDirection
            binding.windSpeedView.text = "$windSpeed\nkm/h\n"
            binding.compasView.setParams(windDegree)
            val moreWeatherList =
                listOf(
                    MoreWeatherElem("Real feel(°C)", getModifiableFloat(feelingC)),
                    MoreWeatherElem("Real feel(°F)", getModifiableFloat(feelingF)),
                    MoreWeatherElem(
                        "Humidity",
                        "${getModifiableFloat(humidityPercent)}%"
                    ),
                    MoreWeatherElem(
                        "Cloud cover",
                        "${getModifiableFloat(cloudPercent)}%"
                    ),
                    MoreWeatherElem("Pressure", getModifiableFloat(pressure) + "mbar"),
                    MoreWeatherElem(
                        "Precipitation",
                        getModifiableFloat(precipitationAmountHour) + "mm/h"
                    ),
                    MoreWeatherElem(
                        "Visibility",
                        getModifiableFloat(visibilityKm) + "km/h"
                    ),
                    MoreWeatherElem(
                        "Wind gust",
                        getModifiableFloat(gustWindSpeed) + "km/h"
                    )
                )
            moreWeatherAdapter.setMoreWeather(moreWeatherList)
        }

        binding.sunriseView.text = "Sunrise:\n${weather.daysForecasts[0].sunrise}"
        binding.sunsetView.text = "Sunset:\n${weather.daysForecasts[0].sunset}"

        barChartAdapter.setWeather(
            getBarChartList(
                weather.daysForecasts[0].hourWeathers,
                weather.location.time
            )
        )
    }

    private fun setInvisibleParams() {
        binding.groupView.visibility = View.INVISIBLE
    }

    private fun setVisibleParams() {
        with(binding) {
            parentLayout.setBackgroundResource(R.drawable.background)
            groupView.visibility = View.VISIBLE
            lottieView.visibility = View.GONE
        }
    }

    private fun initRecyclers() {
        binding.threeDayForecastRecycler.apply {
            adapter = generalWeatherAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        binding.moreInfoRecycler.apply {
            adapter = moreWeatherAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val nextClick: (WeatherModel) -> Unit = {
            weatherViewModel.getHourWeatherInfo(it)
            showHourDialog()
        }

        barChartAdapter = HourWeatherAdapter(nextClick)
        binding.forecastRecycler.apply {
            adapter = barChartAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setButtonsClickListeners() {
        binding.weekForecastButton.setOnClickListener {
            val action =
                CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToFutureWeatherFragment()
            findNavController().navigate(action)
        }

        binding.addButton.setOnClickListener {
            navigateToSearchFragment()
        }

        binding.optionsButton.setOnClickListener {
            showOptionsDialog()
        }

        binding.cityInfoButton.setOnClickListener {
            showCityInfoDialog()
        }
    }

    private fun showOptionsDialog() {
        val builder = AlertDialog.Builder(requireContext())

        val dialogLayout = OptionsDialogLayoutBinding.inflate(layoutInflater, null, false)
        builder.setView(dialogLayout.root)
        val alertDialog = builder.create()
        alertDialog.window?.apply {
            val layoutParams = attributes
            layoutParams.gravity = Gravity.TOP or Gravity.END
            layoutParams.y = 290
            attributes = layoutParams
            setBackgroundDrawableResource(R.drawable.background_constraint)
        }

        dialogLayout.citiesButton.setOnClickListener {
            val action =
                CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToAddedCitiesFragment()
            findNavController().navigate(action)
            alertDialog.dismiss()
        }
        dialogLayout.mapsButton.setOnClickListener {
            val action =
                CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToMapsFragment()
            findNavController().navigate(action)
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showCityInfoDialog() {
        val builder = AlertDialog.Builder(requireContext())

        val dialogLayout = CityDialogLayoutBinding.inflate(layoutInflater, null, false)
        builder.setView(dialogLayout.root)
        val alertDialog = builder.create()

        alertDialog.window?.apply {
            val layoutParams = attributes
            layoutParams.gravity = Gravity.TOP or Gravity.END
            layoutParams.y = 355
            attributes = layoutParams
        }

        with(dialogLayout) {
            regionView.text = getString(R.string.city_region) + locationModel.region
            countryView.text = getString(R.string.city_country) + locationModel.country
            latitudeView.text = getString(R.string.city_latitude) + locationModel.latitude
            longitudeView.text = getString(R.string.city_longitude) + locationModel.longitude
            timeZoneView.text = getString(R.string.city_timezone) + locationModel.timeZone
            textClock.timeZone = locationModel.timeZone
        }

        alertDialog.show()
    }

    private fun showHourDialog() {
        val builder = AlertDialog.Builder(requireContext())

        val dialogLayout = HourDialogLayoutBinding.inflate(layoutInflater, null, false)
        builder.setView(dialogLayout.root)
        val alertDialog = builder.create()

        weatherViewModel.hourWeatherLiveData.observe(viewLifecycleOwner) {
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
                Glide.with(this@CurrentWeatherFragment)
                    .load("https:" + it.icon)
                    .into(iconView)

                windDirView.text = it.windDirection
                compasView.setParams(it.windDegree)
            }
        }

        alertDialog.show()
    }

    private fun navigateToSearchFragment() {
        val action =
            CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun getModifiableFloat(value: Float): String {
        val str = value.toString().split(".")
        if (str[1].toFloat() != 0f) return value.toString()
        return str[0]
    }

    private fun getBarChartList(list: List<HourModel>, time: String): List<HourModel> {

        var index = 0
        for ((ind, value) in time.withIndex()) {
            if (value == ':') {
                index = ind
                break
            }
        }
        val needTime = if (time[index - 2] != ' ') time.substring(
            index - 2,
            index
        ) else time.substring(index - 1, index)
        return list.subList(needTime.toInt(), list.size)
    }

    override fun onResume() {
        super.onResume()
        if (isPaused) {
            weatherViewModel.updateLiveData.observe(viewLifecycleOwner) {
                isPaused = false
                if (it != null) updateData(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isPaused = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}