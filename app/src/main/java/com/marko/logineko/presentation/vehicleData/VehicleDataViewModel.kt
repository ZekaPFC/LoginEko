package com.marko.logineko.presentation.vehicleData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marko.logineko.data.DataState
import com.marko.logineko.domain.exception.Cause
import com.marko.logineko.domain.vehicleData.VehicleData
import com.marko.logineko.domain.vehicleData.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleDataViewModel @Inject constructor(private val vehicleRepo: VehicleRepository) :
    ViewModel() {
    private val vehicleData: MutableList<VehicleData> = mutableListOf()
    private val _uiState: MutableStateFlow<VehicleDataUIState> =
        MutableStateFlow(VehicleDataUIState.Loading)
    val uiState: StateFlow<VehicleDataUIState> = _uiState
    private lateinit var job: Job

    fun getVehicleData() {
        job = viewModelScope.launch {
            vehicleRepo.getVehicleData()
                .collect {
                    mapUiState(it)
                }
        }
    }

    private fun mapUiState(networkState: DataState<List<VehicleData>>) {
        when (networkState) {
            is DataState.Success -> networkSuccess(networkState.data)
            is DataState.Error -> dataError(networkState.cause)
            is DataState.Loading -> _uiState.value = VehicleDataUIState.Loading
        }
    }

    private fun networkSuccess(data: List<VehicleData>) {
        this.vehicleData.clear()
        if (data.isEmpty()) {
            _uiState.value = VehicleDataUIState.EmptyData
        } else {
            this.vehicleData.addAll(data)
            _uiState.value = VehicleDataUIState.HasData(this.vehicleData)
        }
    }

    private fun dataError(cause: Cause) {
        if (this.vehicleData.isEmpty()) {
            _uiState.value = VehicleDataUIState.EmptyData
        } else {
            _uiState.value = VehicleDataUIState.HasData(vehicleData)
        }
        _uiState.value = VehicleDataUIState.Error(cause)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
