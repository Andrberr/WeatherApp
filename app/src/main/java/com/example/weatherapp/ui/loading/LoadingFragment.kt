package com.example.weatherapp.ui.loading

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.factories.ViewModelFactory
import com.example.weatherapp.databinding.FragmentLoadingBinding
import com.example.weatherapp.ui.CitiesViewModel
import com.example.weatherapp.ui.MainActivity
import javax.inject.Inject

class LoadingFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: CitiesViewModel by viewModels { factory }

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generalComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.userCityLiveData.observe(viewLifecycleOwner) {
            val action =
                if (it != "") LoadingFragmentDirections.actionLoadingFragmentToCurrentWeatherFragment(
                    true,
                    true
                )
                else LoadingFragmentDirections.actionLoadingFragmentToSearchFragment(true)
            findNavController().navigate(action)
        }
        viewModel.getUserCity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}