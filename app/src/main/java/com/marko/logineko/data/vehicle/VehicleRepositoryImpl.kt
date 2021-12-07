package com.marko.logineko.data.vehicle

import com.marko.logineko.data.DataState
import com.marko.logineko.data.network.ApiService
import com.marko.logineko.domain.vehicleData.VehicleData
import com.marko.logineko.domain.vehicleData.VehiclePath
import com.marko.logineko.domain.vehicleData.VehicleRepository
import com.marko.logineko.utils.executeRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    VehicleRepository {
    override fun getVehicleData(): Flow<DataState<List<VehicleData>>> {
        return executeRequest { apiService.getVehicleData() }
    }

    override fun getVehiclePathForDate(date: String): Flow<DataState<List<VehiclePath>>> {
        return executeRequest { apiService.getVehiclePathForDate(date) }
    }
}
