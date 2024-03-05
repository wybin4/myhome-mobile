package com.example.myhome.utils.converters

import com.example.myhome.presentation.utils.converters.DoubleNullableConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class DoubleNullableConverterTest {

    private val converter = object : DoubleNullableConverter {}

    @Test
    fun `x2doubleToString returns correct string when both values are not null`() {
        val value = 10.0
        val another = "гКал"
        val expectedResult = "10.0 гКал"

        val result = converter.x2doubleToString(value, another)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `x2doubleToString returns correct string when value is null`() {
        val value: Double? = null
        val another = "гКал"
        val expectedResult = "—"

        val result = converter.x2doubleToString(value, another)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `doubleToString returns correct string when value is not null`() {
        val value = 10.0
        val expectedResult = "10.0"

        val result = converter.doubleToString(value)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `doubleToString returns correct string when value is null`() {
        val value: Double? = null
        val expectedResult = "—"

        val result = converter.doubleToString(value)

        assertEquals(expectedResult, result)
    }
}
