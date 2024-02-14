package com.example.myhome.meter.repositories

import com.example.myhome.base.mappers.DateMapper
import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.mappers.MeterRemoteMapper
import com.example.myhome.meter.mappers.ReadingRemoteMapper
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.models.ReadingGetModel
import com.example.myhome.meter.storages.MeterStorage
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import java.util.Date

class ReadingRepositoryImplTest {
    private lateinit var readingRepository: ReadingRepository
    private lateinit var meterStorage: MeterStorage
    private lateinit var date: Date
    private lateinit var dateString: String

    @Before
    fun setUp() {
        meterStorage = Mockito.mock(MeterStorage::class.java)

        val dateMapper = DateMapper()
        val readingRemoteMapper = ReadingRemoteMapper(dateMapper)
        dateString = "07.02.2024"
        date = dateMapper.mapyyyyMMdd(dateString)
        readingRepository = ReadingRepositoryImpl(meterStorage, readingRemoteMapper)
    }
    @Test
    fun `return correct list`() {
        runBlocking {
            val meterId = 1

            val expected = listOf(
                ReadingGetModel(
                    id = 1,
                    readAt = date,
                    consumption = 1.23,
                    reading = 5.43
                ),
                ReadingGetModel(
                    id = 2,
                    readAt = date,
                    consumption = 0.23,
                    reading = 1.26
                )
            )
            val test = listOf(
                ReadingGetDto(
                    id = 1,
                    readAt = dateString,
                    consumption = 1.23,
                    reading = 5.43
                ),
                ReadingGetDto(
                    id = 2,
                    readAt = dateString,
                    consumption = 0.23,
                    reading = 1.26
                )
            )
            Mockito.`when`(meterStorage.listReading(meterId)).thenReturn(test)
            val actual = readingRepository.listReading(meterId).toList().flatten()
            Assertions.assertEquals(expected, actual)
        }
    }
}