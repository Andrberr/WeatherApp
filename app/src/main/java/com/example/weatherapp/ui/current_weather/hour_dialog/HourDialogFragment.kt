package com.example.weatherapp.ui.current_weather.hour_dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.core.ViewModelFactory
import com.example.weatherapp.R
import com.example.weatherapp.databinding.HourDialogLayoutBinding
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.MainActivity
import javax.inject.Inject

class HourDialogFragment : DialogFragment() {

    private var _binding: HourDialogLayoutBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: GeneralViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generalComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HourDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.hourWeatherLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                cloudPercent.text = it.cloudPercent.toString()
                feelingC.text = it.feelingC.toString()
                feelingF.text = it.feelingF.toString()
                gustWindSpeed.text = it.gustWindSpeed.toString()
                humidityPercent.text = it.humidityPercent.toString()
                precipitationAmountHour.text = it.precipitationAmountHour.toString()
                pressure.text = it.pressure.toString()
                visibilityKm.text = it.visibilityKm.toString()
                windSpeed.text = it.windSpeed.toString()
                Glide.with(this@HourDialogFragment)
                    .load("https:" + it.icon)
                    .into(iconView)

                binding.windDirView.text = it.windDirection
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(R.drawable.background_constraint)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
