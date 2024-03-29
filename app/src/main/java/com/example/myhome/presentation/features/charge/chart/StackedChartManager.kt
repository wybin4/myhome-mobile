package com.example.myhome.presentation.features.charge.chart

import androidx.fragment.app.FragmentActivity
import com.example.myhome.R
import com.example.myhome.presentation.features.charge.models.ChargeChartModel
import com.example.myhome.presentation.utils.pickers.ColorPicker
import com.github.mikephil.charting.charts.BarChart

class StackedChartManager(
    context: FragmentActivity,
    chart: BarChart, chartData: List<ChargeChartModel>
) : ColorPicker {
    companion object {
        private const val MIN_REFERENCE = 0L
    }

    private val referenceTimestamp = chartData.minOfOrNull { apartmentData ->
        apartmentData.charges.minOfOrNull { it.createdAt.time } ?: Long.MAX_VALUE
    } ?: MIN_REFERENCE

    private val primaryColor = getColor(context, R.color.primary)

    private val dataProvider = StackedChartDataProvider(
        chart, chartData, context,
        primaryColor, referenceTimestamp
    )
    private val uiProvider = StackedChartUiProvider(
        chart, context, referenceTimestamp, primaryColor
    )

    init {
        dataProvider.setupChart()
        uiProvider.setupChart()
        chart.invalidate()
    }

}