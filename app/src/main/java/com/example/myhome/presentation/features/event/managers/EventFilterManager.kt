package com.example.myhome.presentation.features.event.managers

import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.EventFilterViewBinding
import com.example.myhome.features.event.models.EventTypeResponse
import com.example.myhome.presentation.utils.filters.FilterButton
import com.example.myhome.presentation.utils.filters.FilterDatePicker
import java.util.Date

class EventFilterManager(
    context: FragmentActivity,
    binding: EventFilterViewBinding,
    setStatusList: (List<EventTypeResponse>) -> Unit,
    getStatusList: () -> List<EventTypeResponse>,
    setCreatedAt: (Date?, Date?) -> Unit
) {
    private val createdAtPicker = FilterDatePicker(
        context,
        setCreatedAt, binding.createdAt
    )

    init {
        binding.createdAt.setOnClickListener {
            createdAtPicker.show()
        }

        FilterButton(
            binding.votingButtonActive, binding.votingButtonNotActive,
            setStatusList, getStatusList, EventTypeResponse.Voting
        )

        FilterButton(
            binding.notificationButtonActive, binding.notificationButtonNotActive,
            setStatusList, getStatusList, EventTypeResponse.Notification
        )
    }
}
