package com.example.weatherapp.ui.added_cities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.databinding.FragmentAddedCitiesBinding
import com.example.weatherapp.ui.MainActivity

class AddedCitiesFragment : Fragment() {

    private var _binding: FragmentAddedCitiesBinding? = null
    private val binding get() = _binding!!

    private val args: AddedCitiesFragmentArgs by navArgs()

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

        binding.backButton.setOnClickListener {
            val action = AddedCitiesFragmentDirections.actionAddedCitiesFragmentToCurrentWeatherFragment(chosenCity)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}