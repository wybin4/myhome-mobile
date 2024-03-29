package com.example.myhome.presentation.features.charge.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.databinding.ChargeListViewHeaderBinding

class ChargeListHeaderAdapter(
    private val binding: ChargeListViewHeaderBinding
) : RecyclerView.Adapter<ChargeListHeaderAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    inner class HeaderViewHolder(private val binding: ChargeListViewHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

}