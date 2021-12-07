package com.marko.logineko.domain.vehicleData

import com.marko.logineko.data.DataState
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    fun getVehicleData(): Flow<DataState<List<VehicleData>>>
    fun getVehiclePathForDate(date: String): Flow<DataState<List<VehiclePath>>>
}
