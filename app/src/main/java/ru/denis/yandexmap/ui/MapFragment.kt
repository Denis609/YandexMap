package ru.denis.yandexmap.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.runtime.image.ImageProvider
import ru.denis.yandexmap.R
import ru.denis.yandexmap.databinding.MapFragmentBinding

class MapFragment : Fragment(), InputListener {

    private lateinit var binding: MapFragmentBinding
    private val viewModel: CoordinatesViewModel by activityViewModels()

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation.addOnSuccessListener { location: android.location.Location? ->
                location?.let {
                    moveCamera(Point(it.latitude, it.longitude), 10f)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        MapKitFactory.initialize(requireContext())
        binding = MapFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.map.map.addInputListener(this)
        requestMultiplePermissions.launch(arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
        ))
        coordinatorObserver()

        binding.confirmSelectedPointFab.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun coordinatorObserver() {
        viewModel.coordinates.observe(viewLifecycleOwner) {
            binding.confirmSelectedPointFab.text = getString(R.string.confirm_selected_point)
            binding.map.map.mapObjects.clear()
            binding.map.map.mapObjects.addPlacemark(
                Point(it.latitude, it.longitude),
                ImageProvider.fromBitmap(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.mark
                    )?.toBitmap(75, 75))
            )
        }
    }

    private fun moveCamera(point: Point, zoom: Float) {
        binding.map.map.move(
            CameraPosition(point, zoom, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1f),
            null)
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onMapTap(map: Map, point: Point) {
        viewModel.setCoordinates(
            latitude = point.latitude,
            longitude = point.longitude
        )
    }

    override fun onMapLongTap(map: Map, point: Point) {}
}