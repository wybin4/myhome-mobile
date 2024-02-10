package com.example.myhome.base.mappers

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import java.text.ParseException
import java.text.SimpleDateFormat

class DateMapperTest {
    private lateinit var mapper: DateMapper
    private lateinit var dateFormat: SimpleDateFormat
    @Before
    fun `setup`() {
        mapper = DateMapper()
        dateFormat = SimpleDateFormat("dd.MM.yyyy")
    }
    @Test
    fun `map valid date format`() {
        val dateString = "07.02.2024"
        val expectedDate = dateFormat.parse(dateString)
        val actualDate = mapper.mapyyyyMMdd(dateString)
        Assertions.assertEquals(expectedDate, actualDate)
    }
    @Test(expected = ParseException::class)
    fun `map invalid date format`() {
        val dateString = "07-02-2024"
        mapper.mapyyyyMMdd(dateString)
    }
    @Test(expected = ParseException::class)
    fun `empty string`() {
        val dateString = ""
        mapper.mapyyyyMMdd(dateString)
    }
}