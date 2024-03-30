package com.example.myhome.presentation.features.appeal

import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.FilterViewBinding
import com.example.myhome.features.appeal.models.AppealStatus
import com.example.myhome.presentation.utils.filters.FilterButton
import com.example.myhome.presentation.utils.filters.FilterDatePicker
import java.util.Date

class AppealFilterManager(
    context: FragmentActivity,
    binding: FilterViewBinding,
    setStatusList: (List<AppealStatus>) -> Unit,
    getStatusList: () -> List<AppealStatus>,
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
            binding.closedButtonActive, binding.closedButtonNotActive,
            setStatusList, getStatusList, AppealStatus.Closed
        )

        FilterButton(
            binding.processingButtonActive, binding.processingButtonNotActive,
            setStatusList, getStatusList, AppealStatus.Processing
        )

        FilterButton(
            binding.rejectedButtonActive, binding.rejectedButtonNotActive,
            setStatusList, getStatusList, AppealStatus.Rejected
        )
    }
}
