package com.example.myhome.presentation.utils.filters

import androidx.fragment.app.FragmentActivity
import com.example.myhome.models.DateConverter
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.util.Date

class FilterDatePicker(
    private val activity: FragmentActivity,
    private val setDateRange: (Date?, Date?) -> Unit,
    private val attribute: TextInputEditText
) : DateConverter {

    private val datePicker: MaterialDatePicker<androidx.core.util.Pair<Long, Long>> = MaterialDatePicker.Builder.dateRangePicker()
        .setTitleText("Выберите дату")
        .build()

    init {
        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedStartDate = selection.first?.let { Date(it) }
            val selectedEndDate = selection.second?.let { Date(it) }
            setDateRange(selectedStartDate, selectedEndDate)
            updateAttributeText(selectedStartDate, selectedEndDate)
        }
    }

    private fun updateAttributeText(startDate: Date?, endDate: Date?) {
        val formattedStartDate = startDate?.let { formatDate(it) }
        val formattedEndDate = endDate?.let { formatDate(it) }
        val text = if (startDate != null && endDate != null) {
            "$formattedStartDate - $formattedEndDate"
        } else if (startDate != null) {
            formattedStartDate
        } else if (endDate != null) {
            formattedEndDate
        } else {
            ""
        }
        attribute.setText(text)
    }

    fun show() {
        datePicker.show(activity.supportFragmentManager, "DateRangePicker")
    }
}