package com.example.myhome.meter.repositories

import com.example.myhome.DateMapper
import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.mappers.MeterRemoteMapper
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.storages.MeterStorage
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.Mockito.mock

class MeterRepositoryTest {
    private val meterRemoteMapper = MeterRemoteMapper()
    private val meterStorage = mock(MeterStorage::class.java)
    private val meterRepository = MeterRepositoryImpl(meterStorage, meterRemoteMapper)
    private val dateMapper = DateMapper()

    private val dateString = "07.02.2024"
    private val date = dateMapper.mapyyyyMMdd(dateString)

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
                    typeOfServiceEngName = "Electricity",
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
                    typeOfServiceEngName = "Water",
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
                    typeOfServiceEngName = "Electricity",
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
                    typeOfServiceEngName = "Water",
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