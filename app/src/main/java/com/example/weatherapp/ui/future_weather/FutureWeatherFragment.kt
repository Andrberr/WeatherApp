package com.example.weatherapp.ui.future_weather

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.ui.MainActivity
import com.example.core.ViewModelFactory
import com.example.domain.models.DayWeather
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFutureWeatherBinding
import com.example.weatherapp.ui.GeneralViewModel
import javax.inject.Inject

class FutureWeatherFragment : Fragment() {

    private var _binding: FragmentFutureWeatherBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val vm: GeneralViewModel by viewModels { factory }

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
                        FutureWeatherFragmentDirections.actionFutureWeatherFragmentToCurrentWeatherFragment(
                            true,
                            false
                        )
                    findNavController().navigate(action)
                }
            })

        _binding = FragmentFutureWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val itemClick: (DayWeather) -> Unit = {
            vm.getDayWeather(it)
            val action =
                FutureWeatherFragmentDirections.actionFutureWeatherFragmentToDayWeatherFragment()
            findNavController().navigate(action)
        }

        val adapter = FutureWeatherAdapter(itemClick)
        binding.forecastRecycler.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        vm.weatherLiveData.observe(viewLifecycleOwner) {
           if (it != null) adapter.setWeather(it.daysForecasts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}