package com.example.myhome.presentation.features.charge.chart.providers

import androidx.fragment.app.FragmentActivity
import com.example.myhome.R
import com.example.myhome.presentation.features.charge.chart.CustomValueFormatter
import com.example.myhome.presentation.features.charge.chart.renderers.CustomXAxisRenderer
import com.example.myhome.presentation.features.charge.chart.renderers.RoundedBarChartRenderer
import com.example.myhome.presentation.utils.pickers.ColorPicker
import com.example.myhome.presentation.utils.pickers.ThemePicker
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis

class StackedChartUiProvider(
    private val chart: BarChart, private val context: FragmentActivity,
    private val referenceTimestamp: Long, private val primaryColor: Int
) : ThemePicker, ColorPicker {
    private val primaryLightColor = getColor(context, R.color.primary_light)
    private val whiteColor = getColor(context, R.color.white)
    private val blackColor = getColor(context, R.color.black)

    fun setupChart() {
        chart.apply {
            val chartRenderer = RoundedBarChartRenderer(this, this.animator, this.viewPortHandler)
            chartRenderer.setRadius(22)
            renderer = chartRenderer

            description = null
            axisRight.isEnabled = false
            extraBottomOffset = 20f
            setFitBars(true)
            setTouchEnabled(false)
            isDragEnabled = false
        }

        setupXAxis()
        setupYAxis()
        setupLegend()
    }

    private fun setupLegend() {
        val isItDarkTheme = isDarkTheme(context)
        chart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            orientation = Legend.LegendOrientation.HORIZONTAL
            form = Legend.LegendForm.CIRCLE
            xOffset = -30f
            yOffset = 16f
            setDrawInside(false)
            isWordWrapEnabled = true

            textSize = 12f
            textColor = if (isItDarkTheme) {
                whiteColor
            } else {
                blackColor
            }
        }
    }

    private fun setupYAxis() {
        chart.axisLeft.apply {
            setDrawAxisLine(false)
            gridColor = primaryLightColor
            textColor = primaryColor
            gridLineWidth = 1f
            axisMinimum = 0f
        }
    }

    private fun setupXAxis() {
        chart.apply {
            val xAxisRenderer = CustomXAxisRenderer(
                this.viewPortHandler, this.xAxis, this.getTransformer(YAxis.AxisDependency.LEFT)
            )
            this.setXAxisRenderer(xAxisRenderer)
        }

        chart.xAxis.apply {
            this.valueFormatter = CustomValueFormatter(referenceTimestamp)

            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            setDrawAxisLine(false)
            granularity = 1f
            isGranularityEnabled = true
            textColor = primaryColor
        }
    }

}