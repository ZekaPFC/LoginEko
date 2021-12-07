package com.marko.logineko.data.network

import com.marko.logineko.domain.vehicleData.VehicleData
import com.marko.logineko.domain.vehicleData.VehiclePath
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("getVehicleData")
    suspend fun getVehicleData(): List<VehicleData>

    @GET("getVehiclePath/{date}")
    suspend fun getVehiclePathForDate(@Path("date") date: String): List<VehiclePath>
}
