package com.marko.logineko.presentation.vehicleData

import com.marko.logineko.domain.exception.Cause
import com.marko.logineko.domain.vehicleData.VehicleData

sealed class VehicleDataUIState {
    data class HasData(val vehicleData: List<VehicleData>) : VehicleDataUIState()
    data class Error(val cause: Cause) : VehicleDataUIState()
    object Loading : VehicleDataUIState()
    object EmptyData : VehicleDataUIState()
}
