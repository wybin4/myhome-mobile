package com.example.myhome.presentation.features.charge.chart

import android.graphics.Color
import com.google.android.material.math.MathUtils

interface ColorGenerator {
    fun generateShadesOfColor(baseColor: Int, targetColor: Int, count: Int): List<Int> {
        val baseHsv = FloatArray(3)
        val targetHsv = FloatArray(3)
        Color.colorToHSV(baseColor, baseHsv)
        Color.colorToHSV(targetColor, targetHsv)

        val shades = mutableListOf<Int>()
        for (i in count - 1 downTo 0) {
            val fraction = i.toFloat() / (count - 1)
            val interpolatedHsv = floatArrayOf(
                MathUtils.lerp(baseHsv[0], targetHsv[0], fraction),
                MathUtils.lerp(baseHsv[1], targetHsv[1], fraction),
                MathUtils.lerp(baseHsv[2], targetHsv[2], fraction)
            )
            shades.add(Color.HSVToColor(interpolatedHsv))
        }
        return shades
    }
}