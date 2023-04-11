package com.example.weatherapp.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.core.ViewModelFactory
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMapsBinding
import com.example.weatherapp.ui.CitiesViewModel
import com.example.weatherapp.ui.GeneralViewModel
import com.example.weatherapp.ui.MainActivity
import com.google.android.gms.location.*
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
    private val weatherViewModel: GeneralViewModel by viewModels { factory }
    private val citiesViewModel: CitiesViewModel by viewModels { factory }

    private val mapView by lazy {
        binding.mapView
    }

    private val locationRequest = createLocationRequest()

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    val point = Point(location.latitude, location.longitude)
                    putMarker(point)
                    stopLocationUpdates()
                }
            }
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION,
                false
            ) -> {
                startLocationUpdates()
            }
            permissions.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                false
            ) -> {
                startLocationUpdates()
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.location_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
        MapKitInitializer.initialize(API_KEY, requireContext())
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        var prevCity = ""
        var isSame = false

        citiesViewModel.userCityLiveData.observe(viewLifecycleOwner) {
            prevCity = it
        }
        citiesViewModel.getUserCity()

        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            if (it.location.city == prevCity) isSame = true
            citiesViewModel.setUserCity(it.location.city)
        }

        binding.searchWeatherButton.setOnClickListener {
            if (coordinates.isNotEmpty()) {
                weatherViewModel.getWeatherInfo(prevCity, coordinates)
                val action = MapsFragmentDirections.actionMapsFragmentToCurrentWeatherFragment(
                    prevCity,
                    isSame,
                    false
                )
                findNavController().navigate(action)
            }
        }
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
        coordinates = point.latitude.toString() + "," + point.longitude.toString()
    }

    private fun createLocationRequest(): LocationRequest {
        val timeInterval = 1000L
        return LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, timeInterval
        ).build()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
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

    companion object {
        private const val API_KEY = "460365c5-1066-4073-96c6-4089e0d7ce5a"
    }
}