package com.example.weatherapp.ui.cities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.data.di.vm_factory.ViewModelFactory
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.GeneralViewModel
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val generalViewModel: GeneralViewModel by viewModels { factory }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generalComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val itemClick: (String) -> Unit = {
            val action = SearchFragmentDirections.actionSearchFragmentToCurrentWeatherFragment(it)
            findNavController().navigate(action)
        }

        val citiesAdapter = CitiesAdapter(itemClick)
        binding.recycler.apply {
            this.adapter = citiesAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val citiesList = mutableListOf<String>()

        val editText = binding.findCityText
        editText.addTextChangedListener { text ->
            val cities = if (text.toString() != "")
                generalViewModel.searchForCities(citiesList, text.toString())
            else citiesList
            citiesAdapter.setCities(cities)
        }

        generalViewModel.citiesLiveData.observe(viewLifecycleOwner) {
            citiesList.clear()
            citiesList.addAll(it)
            citiesAdapter.setCities(it)
        }
        generalViewModel.getCities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}