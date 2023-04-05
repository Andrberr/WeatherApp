package com.example.weatherapp.ui.added_cities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ViewModelFactory
import com.example.domain.models.AddedCityInfo
import com.example.weatherapp.databinding.FragmentAddedCitiesBinding
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.MainActivity
import javax.inject.Inject

class AddedCitiesFragment : Fragment() {

    private var _binding: FragmentAddedCitiesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: GeneralViewModel by viewModels { factory }

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
        _binding = FragmentAddedCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var prevCity = ""
        val itemClick: (String) -> Unit = {
            viewModel.setUserCity(it)
            val isSame = prevCity == it
            val action =
                AddedCitiesFragmentDirections.actionAddedCitiesFragmentToCurrentWeatherFragment(
                    prevCity, isSame, true
                )
            findNavController().navigate(action)
        }

        val deleteButtonClick: (String) -> Unit = {
            viewModel.deleteCityFromDataBase(it)
            deleteElementFromList(it)
            setCitiesForAdapter()
        }

        addedCitiesAdapter = AddedCitiesAdapter(itemClick, deleteButtonClick)
        binding.recycler.apply {
            this.adapter = addedCitiesAdapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.addedCitiesLiveData.observe(viewLifecycleOwner) {
            addedCitiesAdapter.setCities(it)
            addedCitiesList.clear()
            addedCitiesList.addAll(it)
        }
        viewModel.getAddedCities()

        viewModel.userCityLiveData.observe(viewLifecycleOwner) {
            prevCity = it
        }
        viewModel.getUserCity()
    }

    private fun setCitiesForAdapter() {
        addedCitiesAdapter.setCities(addedCitiesList)
    }

    private fun deleteElementFromList(city: String){
        for ((k, elem) in addedCitiesList.withIndex()){
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