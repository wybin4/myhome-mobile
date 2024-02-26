package com.example.myhome.utils.listeners

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import java.util.Date

class DatePickerListener : TextInputEditText {
    private var dateChangeListener: ((Date) -> Unit)? = null

    fun setDateChangeListener(listener: (Date) -> Unit) {
        dateChangeListener = listener
    }

    fun notifyDateChanged(date: Date) {
        dateChangeListener?.invoke(date)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

}
