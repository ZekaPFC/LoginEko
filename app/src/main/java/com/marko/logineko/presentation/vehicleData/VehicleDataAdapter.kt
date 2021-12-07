package com.marko.logineko.presentation.vehicleData

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.marko.logineko.R
import com.marko.logineko.databinding.VehicleDataItemBinding
import com.marko.logineko.domain.vehicleData.VehicleData
import com.marko.logineko.utils.ItemClickListener

class VehicleDataAdapter(
    private val items: MutableList<VehicleData>,
    private val itemClick: ItemClickListener<String>
) :
    RecyclerView.Adapter<VehicleDataHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleDataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VehicleDataItemBinding>(
            inflater,
            R.layout.vehicle_data_item,
            parent,
            false
        )
        return VehicleDataHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: VehicleDataHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<VehicleData>, clear: Boolean) {
        if (clear) {
            this.items.clear()
        }
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
