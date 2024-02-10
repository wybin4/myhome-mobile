package com.example.myhome.presentation

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputEditText
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CustomDatePicker(private val activity: FragmentActivity, private val attribute: TextInputEditText) {

    private var selectedDate: Long? = null
    private val now = Calendar.getInstance()
    private val datePicker: DatePickerDialog = DatePickerDialog.newInstance(
        { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            selectedDate = calendar.timeInMillis
            attribute.setText(formatDate(selectedDate!!))
        },
        now[Calendar.YEAR],
        now[Calendar.MONTH],
        now[Calendar.DAY_OF_MONTH]
    ).apply {
        setTitle("Выберите дату")
        locale = Locale.Builder().setLanguage("ru").setScript("Cyrl").build()
        setOkText("Выбрать")
        setCancelText("Отмена")
    }

    fun show() {
        datePicker.show(activity.supportFragmentManager, "DatePicker")
    }

    private fun formatDate(milliseconds: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val date = Date(milliseconds)
        return dateFormat.format(date)
    }
}
