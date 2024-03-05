package com.example.myhome.presentation.utils.managers

import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DatePickersViewBinding
import com.example.myhome.presentation.utils.pickers.CustomDatePicker
import java.util.Date
import kotlin.reflect.KMutableProperty0

class DatePickersManager(
    private val binding: DatePickersViewBinding,
    private val fragment: FragmentActivity,
    private val verifiedAt: KMutableProperty0<Date?>,
    private val issuedAt: KMutableProperty0<Date?>
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
            verifiedAt, binding.verifiedAt, binding.verifiedAtDatePicker,
            "поверки"
        )
        binding.verifiedAt.setOnClickListener {
            verifiedAtPicker.show()
        }

        issuedAtPicker = CustomDatePicker(
            fragment,
            issuedAt, binding.issuedAt, binding.issuedAtDatePicker,
            "истечения"
        )
        binding.issuedAt.setOnClickListener {
            issuedAtPicker.show()
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setVerifiedAt(verifiedAt: Date) {
        verifiedAtPicker.setDate(verifiedAt)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setIssuedAt(issuedAd: Date) {
        issuedAtPicker.setDate(issuedAd)
    }

}