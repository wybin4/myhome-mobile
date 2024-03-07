package com.example.myhome.presentation.features.event.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.databinding.HouseNotificationListItemBinding
import com.example.myhome.databinding.OptionItemBinding
import com.example.myhome.databinding.VotingListItemBinding
import com.example.myhome.features.event.models.EventType
import com.example.myhome.presentation.features.event.EventUiModel
import com.example.myhome.presentation.features.event.OptionUiModel
import com.example.myhome.presentation.features.event.VotingManager

class EventListAdapter(
    private val context: FragmentActivity,
    private val onOptionClick: ((OptionUiModel) -> Unit)
): ListAdapter<EventUiModel, EventListAdapter.ItemViewHolder>(DiffCallback()) {
    private val votingManager = VotingManager(context, onOptionClick)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            EventType.Notification.ordinal -> HouseNotificationListItemBinding.inflate(layoutInflater, parent, false)
            EventType.Voting.ordinal -> VotingListItemBinding.inflate(layoutInflater, parent, false)
            else -> throw IllegalArgumentException("Unknown view type")
        }
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).eventType.ordinal
    }

    inner class ItemViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventUiModel) {
            binding.apply {
                when (this) {
                    is HouseNotificationListItemBinding -> {
                        notification = item.notification
                    }
                    is VotingListItemBinding -> {
                        voting = item.voting
                        item.voting?.let { votingManager(it, optionsLayout) }
                    }
                    else -> throw IllegalArgumentException("Unknown binding type")
                }
                executePendingBindings()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<EventUiModel>() {
        override fun areItemsTheSame(oldItem: EventUiModel, newItem: EventUiModel): Boolean {
            return oldItem.createdAt == newItem.createdAt
        }

        override fun areContentsTheSame(oldItem: EventUiModel, newItem: EventUiModel): Boolean {
            return oldItem == newItem
        }
    }
}