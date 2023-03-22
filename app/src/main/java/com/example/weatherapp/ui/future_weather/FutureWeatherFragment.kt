package com.example.weatherapp.ui.future_weather

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.data.di.vm_factory.ViewModelFactory
import com.example.weatherapp.databinding.FragmentFutureWeatherBinding
import com.example.weatherapp.ui.WeatherViewModel
import javax.inject.Inject

class FutureWeatherFragment : Fragment() {

    private var _binding: FragmentFutureWeatherBinding? = null
    private val binding get() = _binding!!

    //private val args: FutureWeatherFragmentArgs by navArgs()

    @Inject
    lateinit var factory: ViewModelFactory
    private val vm: WeatherViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).weatherComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFutureWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = FutureWeatherAdapter()
        binding.forecastRecycler.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        vm.weatherLiveData.observe(viewLifecycleOwner) {
            println(it.daysForecasts.size)
            adapter.setWeather(it.daysForecasts)
        }
        vm.getWeatherInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}