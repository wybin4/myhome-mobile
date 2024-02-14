package com.example.myhome.meter.repositories

import com.example.myhome.base.mappers.DateMapper
import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.mappers.MeterRemoteMapper
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
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
            val expectedMeters = listOf(
                MeterGetModel(
                    id = 1,
                    factoryNumber = "1238949637983",
                    verifiedAt = date,
                    issuedAt = date,
                    typeOfServiceId = 1,
                    currentReading = 4.4,
                    typeOfServiceName = "Электроэнергия",
                    unitName = "кВт"
                ),
                MeterGetModel(
                    id = 1,
                    factoryNumber = "1238149637983",
                    verifiedAt = date,
                    issuedAt = date,
                    typeOfServiceId = 1,
                    currentReading = 34.4,
                    typeOfServiceName = "ГВС",
                    unitName = "м3"
                )
            )
            val expectedApartments = listOf(
                ApartmentWithMeterGetModel(
                    id = 1,
                    address = "пер. Соборный 99, кв. 12",
                    meters = expectedMeters
                ),
                ApartmentWithMeterGetModel(
                    id = 2,
                    address = "ул. Малюгина 124, кв. 12",
                    meters = expectedMeters
                )
            )

            val testMeters = listOf(
                MeterGetDto(
                    id = 1,
                    factoryNumber = "1238949637983",
                    verifiedAt = dateString,
                    issuedAt = dateString,
                    typeOfServiceId = 1,
                    currentReading = 4.4,
                    typeOfServiceName = "Электроэнергия",
                    unitName = "кВт"
                ),
                MeterGetDto(
                    id = 1,
                    factoryNumber = "1238149637983",
                    verifiedAt = dateString,
                    issuedAt = dateString,
                    typeOfServiceId = 1,
                    currentReading = 34.4,
                    typeOfServiceName = "ГВС",
                    unitName = "м3"
                )
            )
            val testApartments = listOf(
                ApartmentWithMeterGetDto(
                    apartmentId = 1,
                    apartmentNumber = 12,
                    apartmentFullAddress = "пер. Соборный 99, кв. 12",
                    meters = testMeters
                ),
                ApartmentWithMeterGetDto(
                    apartmentId = 2,
                    apartmentNumber = 12,
                    apartmentFullAddress = "ул. Малюгина 124, кв. 12",
                    meters = testMeters
                )
            )

            Mockito.`when`(meterStorage.listApartmentWithMeter()).thenReturn(testApartments)
            val actual = meterRepository.listApartmentWithMeter().toList().flatten()
            Assertions.assertEquals(expectedApartments, actual)
        }
    }
}