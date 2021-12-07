package com.marko.logineko.presentation.vehiclePath

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.marko.logineko.R
import com.marko.logineko.data.DataState
import com.marko.logineko.databinding.VehiclePathFragmentBinding
import com.marko.logineko.domain.vehicleData.VehiclePath
import com.marko.logineko.utils.includeAll
import com.marko.logineko.utils.launch
import com.marko.logineko.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class VehiclePathFragment : Fragment(), OnMapReadyCallback {
    private object Flipper {
        const val MAP = 0
        const val LOADING = 1
    }

    private lateinit var dataBinding: VehiclePathFragmentBinding
    private lateinit var mapFragment: SupportMapFragment
    private var map: GoogleMap? = null
    private val viewModel by viewModels<VehiclePathViewModel>()
    private val args: VehiclePathFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.vehicle_path_fragment, container, false)
        initMap()
        observeState()
        return dataBinding.root
    }

    private fun initMap() {
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        getVehiclePathData()
    }

    private fun getVehiclePathData() {
        viewModel.getVehiclePath(args.date)
    }

    private fun observeState() {
        launch {
            viewModel.state.collect { state ->
                when (state) {
                    is DataState.Success -> showVehiclePath(state.data)
                    is DataState.Error -> showErrorAndClose()
                    is DataState.Loading -> showLoading()
                }
            }
        }
    }

    private fun showVehiclePath(path: List<VehiclePath>) {
        dataBinding.viewFlipper.displayedChild = Flipper.MAP
        val points = mapPathToLatLang(path)
        val cameraUpdate = prepareCameraUpdate(points)
        val polyline = PolylineOptions().addAll(points)
        map?.moveCamera(cameraUpdate)
        map?.addPolyline(polyline)
    }

    private fun mapPathToLatLang(path: List<VehiclePath>): List<LatLng> {
        return path.map { LatLng(it.latitude, it.longitude) }
    }

    private fun prepareCameraUpdate(points: List<LatLng>): CameraUpdate {
        val latLngBounds = LatLngBounds.Builder().includeAll(points).build()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        return CameraUpdateFactory.newLatLngBounds(latLngBounds, width, height, 15)
    }

    private fun showErrorAndClose() {
        toast(getString(R.string.error_while_loading_path))
        findNavController().navigateUp()
    }

    private fun showLoading() {
        dataBinding.viewFlipper.displayedChild = Flipper.LOADING
    }
}
