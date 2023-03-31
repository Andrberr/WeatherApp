package com.example.weatherapp.ui.loading

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.core.ViewModelFactory
import com.example.weatherapp.databinding.FragmentLoadingBinding
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.MainActivity
import javax.inject.Inject

class LoadingFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: GeneralViewModel by viewModels { factory }

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    private val args: LoadingFragmentArgs by navArgs()

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
            if (it.isEmpty()) {
                val action = LoadingFragmentDirections.actionLoadingFragmentToSearchFragment()
                findNavController().navigate(action)
            } else {
                viewModel.getWeatherInfo(it)
            }
        }
        viewModel.getUserCity()

        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            if (it.location.city != args.city || args.flag) {
                val action =
                    LoadingFragmentDirections.actionLoadingFragmentToCurrentWeatherFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}