package com.example.myhome.meter.repositories

import com.example.myhome.base.mappers.DateMapper
import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.mappers.MeterRemoteMapper
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.storages.MeterStorage
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.Date

class MeterRepositoryImplTest {
    private lateinit var meterRepository: MeterRepository
    private lateinit var meterStorage: MeterStorage
    private lateinit var date: Date
    private lateinit var dateString: String

    @Before
    fun setUp() {
        meterStorage = mock(MeterStorage::class.java)

        val dateMapper = DateMapper()
        val meterRemoteMapper = MeterRemoteMapper(dateMapper)
        dateString = "07.02.2024"
        date = dateMapper.mapyyyyMMdd(dateString)
        meterRepository = MeterRepositoryImpl(meterStorage, meterRemoteMapper)
    }

    @Test
    fun `return correct list`() {
        runBlocking {
            val expected = listOf(
                MeterGetModel(
                    id = 1,
                    factoryNumber = "1238949637983",
                    verifiedAt = date,
                    issuedAt = date,
                    apartmentId = 101,
                    typeOfServiceId = 1,
                    currentReading = 5000,
                    typeOfServiceName = "Электроэнергия",
                    unitName = "кВт"
                ),
                MeterGetModel(
                    id = 1,
                    factoryNumber = "1238149637983",
                    verifiedAt = date,
                    issuedAt = date,
                    apartmentId = 101,
                    typeOfServiceId = 1,
                    currentReading = 5000,
                    typeOfServiceName = "ГВС",
                    unitName = "м3"
                )
            )
            val test = listOf(
                MeterGetDto(
                    id = 1,
                    factoryNumber = "1238949637983",
                    verifiedAt = dateString,
                    issuedAt = dateString,
                    apartmentId = 101,
                    typeOfServiceId = 1,
                    currentReading = 5000,
                    typeOfServiceName = "Электроэнергия",
                    unitName = "кВт"
                ),
                MeterGetDto(
                    id = 1,
                    factoryNumber = "1238149637983",
                    verifiedAt = dateString,
                    issuedAt = dateString,
                    apartmentId = 101,
                    typeOfServiceId = 1,
                    currentReading = 5000,
                    typeOfServiceName = "ГВС",
                    unitName = "м3"
                )
            )
            Mockito.`when`(meterStorage.listMeter()).thenReturn(test)
            val actual = meterRepository.listMeter().toList().flatten()
            Assertions.assertEquals(expected, actual)
        }
    }
}