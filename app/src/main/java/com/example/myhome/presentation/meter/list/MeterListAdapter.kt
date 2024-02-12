package com.example.myhome.presentation.meter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.databinding.MeterListItemBinding
import com.example.myhome.meter.models.MeterGetModel

class MeterListAdapter(
    private val context: Context,
    private val onItemClick: (meter: MeterGetModel) -> Unit
) :
    ListAdapter<MeterGetModel, MeterListAdapter.ItemViewHolder>(MeterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MeterListItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val meter = getItem(position)
        holder.bind(meter)
        holder.itemView.setOnClickListener {
            onItemClick(meter)
        }
    }

    class ItemViewHolder(private val binding: MeterListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meter: MeterGetModel) {
            binding.meter = meter
            binding.executePendingBindings()
        }
    }

    class MeterDiffCallback : DiffUtil.ItemCallback<MeterGetModel>() {
        override fun areItemsTheSame(oldItem: MeterGetModel, newItem: MeterGetModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MeterGetModel, newItem: MeterGetModel): Boolean {
            return oldItem == newItem
        }
    }
}
