package com.example.weatherapp.ui.cities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ViewModelFactory
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.ui.CitiesViewModel
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.current_weather.CurrentWeatherFragmentArgs
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val citiesViewModel: CitiesViewModel by viewModels { factory }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val args: SearchFragmentArgs by navArgs()

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
                }
            })

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.findCityText.visibility = View.INVISIBLE
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val itemClick: (String) -> Unit = {
            citiesViewModel.setUserCity(it)

            val action = SearchFragmentDirections.actionSearchFragmentToCurrentWeatherFragment(
                args.update,
                true
            )
            findNavController().navigate(action)
        }

        val citiesAdapter = CitiesAdapter(itemClick)
        binding.recycler.apply {
            this.adapter = citiesAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val citiesList = mutableListOf<String>()
        citiesViewModel.citiesLiveData.observe(viewLifecycleOwner) {
            binding.findCityText.visibility = View.VISIBLE
            binding.lottieView.visibility = View.GONE
            citiesList.clear()
            citiesList.addAll(it)
            citiesAdapter.setCities(it)
        }
        citiesViewModel.getCities()

        val editText = binding.findCityText
        editText.addTextChangedListener { text ->
            val cities = if (text.toString() != "")
                citiesViewModel.searchForCities(citiesList, text.toString())
            else citiesList
            citiesAdapter.setCities(cities)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}