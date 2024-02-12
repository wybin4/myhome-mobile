package com.example.myhome.presentation

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.reflect.KMutableProperty0

class CustomDatePicker(
    private val activity: FragmentActivity,
    private var selectedDate: KMutableProperty0<Date?>,
    private val attribute: TextInputEditText,
    input: TextInputLayout,
    validateMessage: String
) {
    private val validator: InputValidator = InputValidator(
        input,
        { text: String? -> text?.length!! > 0 },"Выберите дату $validateMessage", { validate() }
    )

    private val now = Calendar.getInstance()
    private val datePicker: DatePickerDialog = DatePickerDialog.newInstance(
        { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            selectedDate.set(calendar.time)
            attribute.setText(dateToString())
            validate()
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

    private fun dateToString(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return selectedDate.get()?.let { dateFormat.format(it) } ?: ""
    }

    fun validate(): Boolean {
        return validator.validate(dateToString())
    }

}
