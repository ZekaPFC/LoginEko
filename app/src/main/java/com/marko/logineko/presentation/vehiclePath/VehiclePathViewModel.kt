package com.marko.logineko.presentation.vehiclePath

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marko.logineko.data.DataState
import com.marko.logineko.domain.vehicleData.VehiclePath
import com.marko.logineko.domain.vehicleData.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclePathViewModel @Inject constructor(private val repository: VehicleRepository) :
    ViewModel() {

    private val _state: MutableStateFlow<DataState<List<VehiclePath>>> =
        MutableStateFlow(DataState.Loading)
    val state: StateFlow<DataState<List<VehiclePath>>> = _state

    fun getVehiclePath(date: String) {
        viewModelScope.launch {
            repository.getVehiclePathForDate(date)
                .collect { networkState -> _state.value = networkState }
        }
    }
}
