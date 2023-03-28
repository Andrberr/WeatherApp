package com.example.weatherapp.ui.added_cities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.data.di.vm_factory.ViewModelFactory
import com.example.weatherapp.databinding.FragmentAddedCitiesBinding
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.MainActivity
import javax.inject.Inject

class AddedCitiesFragment : Fragment() {

    private var _binding: FragmentAddedCitiesBinding? = null
    private val binding get() = _binding!!

    private val args: AddedCitiesFragmentArgs by navArgs()

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: GeneralViewModel by viewModels { factory }

    private var chosenCity: String = ""

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
        chosenCity = args.city
//        binding.backButton.setOnClickListener {
//            val action = AddedCitiesFragmentDirections.actionAddedCitiesFragmentToCurrentWeatherFragment(chosenCity)
//            findNavController().navigate(action)
//        }

        val itemClick: (String) -> Unit = {
            val action =
                AddedCitiesFragmentDirections.actionAddedCitiesFragmentToCurrentWeatherFragment(
                    it
                )
            findNavController().navigate(action)
        }

        val adapter = AddedCitiesAdapter(itemClick)
        binding.recycler.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.addedCitiesLiveData.observe(viewLifecycleOwner){
            adapter.setCities(it)
        }
        viewModel.getAddedCities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}