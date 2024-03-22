package com.example.myhome.presentation.features.chat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.databinding.MessageListItemLoadingBinding
import com.example.myhome.presentation.ConstantsUi.Companion.LOADING_RECYCLER_VIEW_MULTIPLIER

class InfiniteMessageListAdapter(
    private val itemList: List<String>
) : RecyclerView.Adapter<InfiniteMessageListAdapter.MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MessageListItemLoadingBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val index = position % itemList.size
        val item = itemList[index]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size * LOADING_RECYCLER_VIEW_MULTIPLIER
    }

    inner class MessageViewHolder(private val binding: MessageListItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.item = item
            when (item) {
                "left" -> {
                    binding.leftItem.visibility = View.VISIBLE
                    binding.rightItem.visibility = View.GONE
                }
                "right" -> {
                    binding.leftItem.visibility = View.GONE
                    binding.rightItem.visibility = View.VISIBLE
                }
                else -> {
                    binding.leftItem.visibility = View.GONE
                    binding.rightItem.visibility = View.GONE
                }
            }
            binding.executePendingBindings()
        }
    }
}