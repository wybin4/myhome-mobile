package com.example.myhome.utils.pickers

import com.example.myhome.presentation.utils.pickers.DigitPicker
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class DigitPickerTest {

    private val digitPicker = DigitPicker()

    @Test
    fun `doubleToString converts digit with three decimal places to the correct format`() {
        val result = digitPicker.doubleToString(12.345)
        assertEquals("12,345", result)
    }

    @Test
    fun `doubleToString converts digit with two decimal places to the correct format`() {
        val result = digitPicker.doubleToString(12.34)
        assertEquals("12,340", result)
    }

    @Test
    fun `doubleToString converts digit with one decimal place digits to the correct format`() {
        val result = digitPicker.doubleToString(12.3)
        assertEquals("12,300", result)
    }

    @Test
    fun `doubleToString converts digit with zero decimal places digits to the correct format`() {
        val result = digitPicker.doubleToString(12.0)
        assertEquals("12,000", result)
    }

    @Test
    fun `stringToDouble converts to the correct format`() {
        val result = digitPicker.stringToDouble("12,345")
        assertEquals(12.345, result)
    }

    @Test
    fun `addDigit add new digit`() {
        val result = digitPicker.addDigit("5", "12,3")
        assertEquals("12,35", result)
    }

    @Test
    fun `addDigit add new digit when previous is zero`() {
        val result = digitPicker.addDigit("5", "0")
        assertEquals("5", result)
    }

    @Test
    fun `addDigit does not allow repeated commas`() {
        val result = digitPicker.addDigit(",", "12,3")
        assertEquals("12,3", result)
    }

    @Test
    fun `addDigit does not allow more than 3 decimal places`() {
        val result = digitPicker.addDigit("2", "12,356")
        assertEquals("12,356", result)
    }

    @Test
    fun `clearDigit removes last digit`() {
        val result = digitPicker.clearDigit("12,345")
        assertEquals("12,34", result)
    }

    @Test
    fun `clearDigit returns zero if single digit`() {
        val result = digitPicker.clearDigit("5")
        assertEquals("0", result)
    }

    @Test
    fun `clearDigit returns null when text is zero`() {
        val result = digitPicker.clearDigit("0")
        assertEquals(null, result)
    }

    @Test
    fun `calcConsumption returns difference string in correct format when new is greater than old`() {
        val result = digitPicker.calcConsumption(15.5, 12.2)
        assertEquals("3,300", result)
    }

    @Test
    fun `calcConsumption returns zero if new is less than old`() {
        val result = digitPicker.calcConsumption(12.2, 15.5)
        assertEquals("0", result)
    }

}