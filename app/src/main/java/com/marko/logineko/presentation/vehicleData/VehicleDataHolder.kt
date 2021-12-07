package com.marko.logineko.presentation.vehicleData

import androidx.recyclerview.widget.RecyclerView
import com.marko.logineko.databinding.VehicleDataItemBinding
import com.marko.logineko.domain.vehicleData.VehicleData
import com.marko.logineko.utils.ItemClickListener

class VehicleDataHolder(
    private val binding: VehicleDataItemBinding,
    private val itemClick: ItemClickListener<String>
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(vehicleData: VehicleData) {
        binding.vehicleData = vehicleData
        binding.itemClick = itemClick
        binding.executePendingBindings()
    }
}
