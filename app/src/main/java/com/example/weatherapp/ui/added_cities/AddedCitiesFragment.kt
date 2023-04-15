package com.example.weatherapp.ui.added_cities

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
import com.example.core.ViewModelFactory
import com.example.domain.models.AddedCityInfo
import com.example.weatherapp.databinding.FragmentAddedCitiesBinding
import com.example.weatherapp.ui.CitiesViewModel
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.MainActivity
import javax.inject.Inject

class AddedCitiesFragment : Fragment() {

    private var _binding: FragmentAddedCitiesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val citiesViewModel: CitiesViewModel by viewModels { factory }
    private val weatherViewModel: GeneralViewModel by viewModels { factory }

    private val addedCitiesList = mutableListOf<AddedCityInfo>()
    private lateinit var addedCitiesAdapter: AddedCitiesAdapter

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
                   if (addedCitiesList.isNotEmpty()) navigateToCurrentWeatherFragment(false)
                }
            })

        _binding = FragmentAddedCitiesBinding.inflate(inflater, container, false)
        binding.lottieView.visibility = View.INVISIBLE
        binding.addButton.visibility = View.INVISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val itemClick: (String) -> Unit = {
            citiesViewModel.setUserCity(it)
            navigateToCurrentWeatherFragment(true)
        }

        val deleteButtonClick: (String) -> Unit = {
            weatherViewModel.deleteCityFromDataBase(it)
            deleteElementFromList(it)
            if (addedCitiesList.isEmpty()) citiesViewModel.setUserCity("")
            setCitiesForAdapter()
        }

        addedCitiesAdapter = AddedCitiesAdapter(itemClick, deleteButtonClick)
        binding.recycler.apply {
            this.adapter = addedCitiesAdapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        citiesViewModel.addedCitiesLiveData.observe(viewLifecycleOwner) {
            addedCitiesAdapter.setCities(it)
            addedCitiesList.clear()
            addedCitiesList.addAll(it)
        }
        citiesViewModel.getAddedCities()
    }

    private fun setCitiesForAdapter() {
        addedCitiesAdapter.setCities(addedCitiesList)
        if (addedCitiesList.isEmpty()) {
            binding.lottieView.visibility = View.VISIBLE
            binding.addButton.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    val action =
                        AddedCitiesFragmentDirections.actionAddedCitiesFragmentToSearchFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun navigateToCurrentWeatherFragment(needUpdate: Boolean) {
        val action =
            AddedCitiesFragmentDirections.actionAddedCitiesFragmentToCurrentWeatherFragment(
                false, needUpdate
            )
        findNavController().navigate(action)
    }

    private fun deleteElementFromList(city: String) {
        for ((k, elem) in addedCitiesList.withIndex()) {
            if (elem.city == city) {
                addedCitiesList.removeAt(k)
                return
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}