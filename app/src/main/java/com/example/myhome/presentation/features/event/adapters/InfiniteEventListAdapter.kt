package com.example.myhome.presentation.features.event.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.databinding.HouseNotificationListItemLoadingBinding
import com.example.myhome.databinding.VotingListItemLoadingBinding
import com.example.myhome.presentation.ConstantsUi.Companion.LOADING_RECYCLER_VIEW_MULTIPLIER

class InfiniteEventListAdapter(
    private val itemList: List<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_NOTIFICATION = 0
        private const val VIEW_TYPE_VOTING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_NOTIFICATION -> {
                val binding = HouseNotificationListItemLoadingBinding.inflate(inflater, parent, false)
                NotificationViewHolder(binding)
            }
            VIEW_TYPE_VOTING -> {
                val binding = VotingListItemLoadingBinding.inflate(inflater, parent, false)
                VotingViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val index = position % itemList.size
        val item = itemList[index]
        when (holder) {
            is NotificationViewHolder -> holder.bind(item)
            is VotingViewHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size * LOADING_RECYCLER_VIEW_MULTIPLIER
    }

    override fun getItemViewType(position: Int): Int {
        val index = position % itemList.size
        return when (itemList[index]) {
            "notification" -> VIEW_TYPE_NOTIFICATION
            "voting" -> VIEW_TYPE_VOTING
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    inner class NotificationViewHolder(private val binding: HouseNotificationListItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    inner class VotingViewHolder(private val binding: VotingListItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

}
