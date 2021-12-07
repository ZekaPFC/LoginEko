package com.marko.logineko.domain.vehicleData

data class VehicleData(
    val date: String,
    val totalWorkingHours: Double,
    val engineSpeed: VehicleStats,
    val engineLoad: VehicleStats,
    val fuelConsumption: VehicleStats
)
