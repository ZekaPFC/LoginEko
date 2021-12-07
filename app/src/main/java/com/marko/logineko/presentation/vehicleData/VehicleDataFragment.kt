package com.marko.logineko.presentation.vehicleData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marko.logineko.R
import com.marko.logineko.databinding.VehicleDataFragmentBinding
import com.marko.logineko.domain.exception.Cause
import com.marko.logineko.domain.vehicleData.VehicleData
import com.marko.logineko.utils.ItemClickListener
import com.marko.logineko.utils.launch
import com.marko.logineko.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class VehicleDataFragment : Fragment() {
    private object Flipper {
        const val HAS_DATA = 0
        const val NO_DATA = 1
        const val LOADING = 2
    }

    private val itemClick: ItemClickListener<String> = object : ItemClickListener<String> {
        override fun onItemClick(item: String) {
            val action =
                VehicleDataFragmentDirections.actionVehicleDataFragmentToVehiclePathFragment(item)
            findNavController().navigate(action)
        }
    }

    private val viewModel by viewModels<VehicleDataViewModel>()
    private lateinit var dataBinding: VehicleDataFragmentBinding
    private val adapter = VehicleDataAdapter(mutableListOf(), itemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.vehicle_data_fragment, container, false)
        init()
        return dataBinding.root
    }

    private fun init() {
        setAdapter()
        getVehicleData()
        observeState()
    }

    private fun getVehicleData() {
        viewModel.getVehicleData()
    }

    private fun observeState() {
        launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is VehicleDataUIState.HasData -> displayVehicleData(state.vehicleData)
                    is VehicleDataUIState.Error -> handleError(state.cause)
                    is VehicleDataUIState.Loading -> loading()
                    is VehicleDataUIState.EmptyData -> noData()
                }
            }
        }
    }

    private fun displayVehicleData(items: List<VehicleData>) {
        dataBinding.viewFlipper.displayedChild = Flipper.HAS_DATA
        adapter.addItems(items, true)
    }

    private fun loading() {
        dataBinding.viewFlipper.displayedChild = Flipper.LOADING
    }

    private fun noData() {
        dataBinding.viewFlipper.displayedChild = Flipper.NO_DATA
    }

    private fun handleError(cause: Cause) {
        when (cause) {
            is Cause.NetworkException -> toast(getString(R.string.no_internet_connection))
            is Cause.GenericError -> toast(getString(R.string.generic_error))
            is Cause.RequestError -> toast(cause.message)
        }
    }

    private fun setAdapter() {
        dataBinding.recyclerView.adapter = adapter
    }
}
