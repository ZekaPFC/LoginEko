package com.marko.logineko.di

import com.marko.logineko.data.vehicle.VehicleRepositoryImpl
import com.marko.logineko.domain.vehicleData.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideVehicleRepository(vehicleRepositoryImpl: VehicleRepositoryImpl): VehicleRepository
}
