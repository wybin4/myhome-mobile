package com.example.myhome.presentation.features.chat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.databinding.MessageCreatedAtItemBinding
import com.example.myhome.databinding.MessageListItemBinding
import com.example.myhome.presentation.features.chat.models.MessageCreatedAtUiModel
import com.example.myhome.presentation.features.chat.models.MessageUiModel
import com.example.myhome.presentation.utils.adapters.AdaptiveIntListAdapter

class MessageListAdapter : ListAdapter<MessageUiModel, MessageListAdapter.MessageViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MessageListItemBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MessageViewHolder(private val binding: MessageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageUiModel) {
            binding.message = item
            binding.executePendingBindings()

            val messageAdapter = AdaptiveStringListAdapter<MessageCreatedAtUiModel, MessageCreatedAtItemBinding>(
                itemBindingInflater = { inflater, parent, attachToParent ->
                    MessageCreatedAtItemBinding.inflate(inflater, parent, attachToParent)
                },
                setBinding = { binding, value ->
                    binding.message = value
                },
                onItemClick = null
            )

            messageAdapter.submitList(item.messages)
            binding.messageGroupRecyclerView.adapter = messageAdapter
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MessageUiModel>() {
        override fun areItemsTheSame(oldItem: MessageUiModel, newItem: MessageUiModel): Boolean {
            return oldItem.createdAt == newItem.createdAt
        }

        override fun areContentsTheSame(oldItem: MessageUiModel, newItem: MessageUiModel): Boolean {
            return oldItem == newItem
        }
    }
}