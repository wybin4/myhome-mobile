package com.example.myhome.utils.pickers

import com.example.myhome.utils.models.TypeOfServiceEngNames
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import com.example.myhome.R

class IconPickerTest {

    private val iconPicker: IconPicker = object : IconPicker {}

    @Test
    fun `getMeterIcon returns correct icon for Gas`() {
        val expected = R.drawable.gas
        val actual = iconPicker.getMeterIcon(TypeOfServiceEngNames.Gas.toString())
        assertEquals(expected, actual)
    }

    @Test
    fun `getMeterIcon returns correct icon for Electricity`() {
        val expected = R.drawable.electricity
        val actual = iconPicker.getMeterIcon(TypeOfServiceEngNames.Electricity.toString())
        assertEquals(expected, actual)
    }

    @Test
    fun `getMeterIcon returns correct icon for Heat`() {
        val expected = R.drawable.heat
        val actual = iconPicker.getMeterIcon(TypeOfServiceEngNames.Heat.toString())
        assertEquals(expected, actual)
    }

    @Test
    fun `getMeterIcon returns correct icon for Water`() {
        val expected = R.drawable.water
        val actual = iconPicker.getMeterIcon(TypeOfServiceEngNames.Water.toString())
        assertEquals(expected, actual)
    }

    @Test
    fun `getMeterIcon returns null for unknown type`() {
        val actual = iconPicker.getMeterIcon("Unknown")
        assertEquals(null, actual)
    }
}