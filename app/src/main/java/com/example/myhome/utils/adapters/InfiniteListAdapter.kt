package com.example.myhome.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.utils.ConstantsUi.Companion.LOADING_RECYCLER_VIEW_MULTIPLIER

class InfiniteListAdapter<T : Any, B : ViewDataBinding>(
    private val itemList: List<T>,
    private val itemBindingInflater: (LayoutInflater, ViewGroup, Boolean) -> B,
    private val setBinding: (B, T) -> Unit
) : RecyclerView.Adapter<InfiniteListAdapter<T, B>.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = itemBindingInflater(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position % itemList.size]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size * LOADING_RECYCLER_VIEW_MULTIPLIER
    }

    inner class ItemViewHolder(val binding: B) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            setBinding(binding, item)
            binding.executePendingBindings()
        }
    }

}
