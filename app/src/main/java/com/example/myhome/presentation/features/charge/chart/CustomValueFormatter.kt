package com.example.myhome.presentation.features.charge.chart

import com.example.myhome.presentation.features.charge.converters.MonthYearConverter
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.Date

class CustomValueFormatter(
    private val referenceTimestamp: Long
) : ValueFormatter(), MonthYearConverter {
    companion object {
        const val MICRO_SECONDS_IN_MONTH = 2419200000L
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val dateInMillis = value.toLong() * MICRO_SECONDS_IN_MONTH
        val originalTimestamp = dateInMillis + referenceTimestamp
        val date = Date(originalTimestamp)
        return formatDateShort(date)
    }

}
