package com.example.myhome.utils.managers

import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DatePickersViewBinding
import com.example.myhome.presentation.appeal.add.BaseAppealViewModel
import com.example.myhome.utils.pickers.CustomDatePicker

class DatePickersManager(
    private val binding: DatePickersViewBinding,
    private val fragment: FragmentActivity,
    private val viewModel: BaseAppealViewModel
) {
    private lateinit var verifiedAtPicker: CustomDatePicker
    private lateinit var issuedAtPicker: CustomDatePicker

    init {
        setup()
    }

    fun isDatePickersValid(): Boolean {
        val isVerifiedAt = verifiedAtPicker.validate()
        val isIssuedAt = issuedAtPicker.validate()
        return isVerifiedAt && isIssuedAt
    }

    private fun setup() {
        verifiedAtPicker = CustomDatePicker(
            fragment,
            viewModel::selectVerifiedAt, binding.verifiedAt, binding.verifiedAtDatePicker,
            "поверки"
        )
        binding.verifiedAt.setOnClickListener {
            verifiedAtPicker.show()
        }

        issuedAtPicker = CustomDatePicker(
            fragment,
            viewModel::selectIssuedAt, binding.issuedAt, binding.issuedAtDatePicker,
            "истечения"
        )
        binding.issuedAt.setOnClickListener {
            issuedAtPicker.show()
        }
    }

}