package com.example.myhome.presentation.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.myhome.presentation.models.AdaptiveInt
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class CustomPagingAdapter<T: AdaptiveInt, B : ViewDataBinding>(
    private val itemBindingInflater: (LayoutInflater, ViewGroup, Boolean) -> B,
    private val setBinding: (B, T) -> Unit,
    private val onItemClick: ((item: T) -> Unit)?
) : PagingDataAdapter<T, CustomPagingAdapter<T, B>.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = itemBindingInflater(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        item?.run {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(this)
            }
        }
    }

    inner class ItemViewHolder(val binding: B) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            setBinding(binding, item)
            binding.executePendingBindings()
        }
    }

    class DiffCallback<T: AdaptiveInt> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areContentsTheSame(newItem)
        }
    }
}
