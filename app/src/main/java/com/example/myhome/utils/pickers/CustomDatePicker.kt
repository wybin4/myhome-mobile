package com.example.myhome.utils.pickers

import androidx.fragment.app.FragmentActivity
import com.example.myhome.common.models.DateConverter
import com.example.myhome.utils.InputValidator
import com.example.myhome.utils.listeners.DatePickerListener
import com.example.myhome.utils.listeners.DateChangeListener
import com.google.android.material.textfield.TextInputLayout
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.reflect.KMutableProperty0

class CustomDatePicker(
    private val activity: FragmentActivity,
    private var selectedDate: KMutableProperty0<Date?>,
    private val attribute: DatePickerListener,
    input: TextInputLayout,
    validateMessage: String
): DateConverter, DateChangeListener {
    init {
        attribute.setDateChangeListener(this::onDateChanged)
    }

    private val validator: InputValidator = InputValidator(
        input,
        { text: String? -> text?.length!! > 0 },"Выберите дату $validateMessage", { validate() }
    )

    private val now = Calendar.getInstance()
    private val datePicker: DatePickerDialog = DatePickerDialog.newInstance(
        { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)

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

    private fun formatDate(): String {
        return selectedDate.get()?.let { formatDate(it) } ?: ""
    }

    fun validate(): Boolean {
        return validator.validate(formatDate())
    }

    override fun onDateChanged(date: Date) {
        selectedDate.set(date)
        attribute.setText(formatDate())
    }

}
