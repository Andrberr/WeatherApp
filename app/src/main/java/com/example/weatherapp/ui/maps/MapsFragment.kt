package com.example.weatherapp.ui.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.core.ViewModelFactory
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMapsBinding
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.MainActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.runtime.image.ImageProvider
import javax.inject.Inject

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: GeneralViewModel by viewModels { factory }

    private val mapView by lazy {
        binding.mapView
    }

    private var coordinates: String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).generalComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey("460365c5-1066-4073-96c6-4089e0d7ce5a");
        MapKitFactory.initialize(requireContext())
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMapClickListener()

        viewModel.locationLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.searchWeatherButton.setOnClickListener {
            if (coordinates.isNotEmpty()) {
                viewModel.getWeatherInfo(coordinates)
                val action = MapsFragmentDirections.actionMapsFragmentToCurrentWeatherFragment("", true, false)
                findNavController().navigate(action)
            }
        }
    }

    private fun setMapClickListener() {
        val inputListener = object : InputListener {

            override fun onMapTap(p0: Map, p1: Point) {
                coordinates = p1.latitude.toString() + "," + p1.longitude.toString()
                println(coordinates)
                putMarker(p1)
            }

            override fun onMapLongTap(p0: Map, p1: Point) {
            }
        }
        mapView.map.addInputListener(inputListener)
    }


    private fun putMarker(point: Point) {
        val markerSize =
            requireContext().resources.getDimensionPixelSize(R.dimen.map_marker_icon_size)
        Glide.with(requireContext()).asBitmap()
            .load(R.drawable.marker)
            .into(object : CustomTarget<Bitmap>(markerSize, markerSize) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                  //  mapView.map.mapObjects.clear()
                    mapView.map.mapObjects.addPlacemark(
                        point,
                        ImageProvider.fromBitmap(resource),
                        IconStyle()
                    )
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        mapView.map.move(
            CameraPosition(point, 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}