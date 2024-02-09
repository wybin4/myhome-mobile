package com.example.myhome.presentation.meter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.databinding.MeterListItemViewBinding
import com.example.myhome.domain.models.MeterGetModel

class MeterListAdapter(private val context: Context) :
    ListAdapter<MeterGetModel, MeterListAdapter.ItemViewHolder>(MeterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MeterListItemViewBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val meter = getItem(position)
        holder.bind(meter)
    }

    class ItemViewHolder(private val binding: MeterListItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
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
