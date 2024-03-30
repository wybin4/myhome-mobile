package com.example.myhome.presentation.features.charge.chart.providers

import androidx.fragment.app.FragmentActivity
import com.example.myhome.R
import com.example.myhome.presentation.features.charge.chart.ColorGenerator
import com.example.myhome.presentation.features.charge.chart.CustomValueFormatter
import com.example.myhome.presentation.features.charge.models.ChargeChartModel
import com.example.myhome.presentation.utils.pickers.ColorPicker
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.util.Calendar
import java.util.Date

class StackedChartDataProvider(
    private val chart: BarChart, private var chartData: List<ChargeChartModel>,
    private val context: FragmentActivity,
    private val firstColor: Int, private val referenceTimestamp: Long
) : ColorGenerator, ColorPicker {
    companion object {
        private const val BAR_WIDTH = 0.46f
        private const val BAR_COUNT = 6
    }
    
    private val secondColor = getColor(context, R.color.primary_light_normal_without_alpha)

    private fun getAxisLabelFloat(date: Date): Float {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date.time
            add(Calendar.MONTH, 0)
            timeInMillis -= referenceTimestamp
        }
        val transformedX = calendar.timeInMillis / CustomValueFormatter.MICRO_SECONDS_IN_MONTH
        return transformedX.toInt().toFloat()
    }

    fun setupChart() {
        val allEntries = mutableListOf<List<BarEntry>>()

        chartData = chartData.sortedByDescending { apartmentData ->
            apartmentData.charges.sumOf { it.amount.toDouble() } / apartmentData.charges.count()
        }

        chartData.forEach { apartmentData ->
            val entries = mutableListOf<BarEntry>()
            apartmentData.charges.forEachIndexed { _, charge ->
                val xValue = getAxisLabelFloat(charge.createdAt)
                entries.add(BarEntry(xValue, charge.amount))
            }
            val size = apartmentData.charges.size
            if (size < BAR_COUNT) {
                for (i in size + 1 until BAR_COUNT) {
                    entries.add(BarEntry(i.toFloat(), 0f))
                }
            }
            allEntries.add(entries)
        }

        val dataSets: MutableList<IBarDataSet> = ArrayList()
        allEntries.forEachIndexed { index, entries ->
            val color = generateShadesOfColor(
                firstColor, secondColor, allEntries.size
            )[index]
            val dataSet = BarDataSet(entries, chartData[index].apartmentName)
            dataSet.setDrawValues(false)
            dataSet.color = color
            dataSets.add(dataSet)
        }

        chart.apply {
            data = BarData(dataSets)
            data.barWidth = BAR_WIDTH
        }
    }

}