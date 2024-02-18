package com.example.myhome.meter.repositories

import com.example.myhome.DateMapper
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.mappers.ReadingRemoteMapper
import com.example.myhome.meter.models.ReadingGetModel
import com.example.myhome.meter.storages.MeterStorage
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito

class ReadingRepositoryTest {
    private val readingRemoteMapper = ReadingRemoteMapper()
    private val meterStorage = Mockito.mock(MeterStorage::class.java)
    private val readingRepository = ReadingRepositoryImpl(meterStorage, readingRemoteMapper)
    private val dateMapper = DateMapper()

    private val dateString = "07.02.2024"
    private val date = dateMapper.mapyyyyMMdd(dateString)

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