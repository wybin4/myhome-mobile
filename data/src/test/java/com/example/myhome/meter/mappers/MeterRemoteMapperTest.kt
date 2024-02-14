package com.example.myhome.meter.mappers

import com.example.myhome.base.mappers.DateMapper
import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterGetModel
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import java.util.Date

class MeterRemoteMapperTest {
    private lateinit var meterMapper: MeterRemoteMapper
    private lateinit var dateMapper: DateMapper
    private lateinit var date: Date
    private lateinit var dateString: String
    @Before
    fun `setup`() {
        dateMapper = DateMapper()
        meterMapper = MeterRemoteMapper(dateMapper)
        dateString = "07.02.2024"
        date = dateMapper.mapyyyyMMdd(dateString)
    }
    @Test
    fun `map correct list`() {
        val extectedMeters = listOf(
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
                meters = extectedMeters
            ),
            ApartmentWithMeterGetModel(
                id = 2,
                address = "ул. Малюгина 124, кв. 12",
                meters = extectedMeters
            )
        )

        val dtoMeters = listOf(
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
        val dtoApartments = listOf(
            ApartmentWithMeterGetDto(
                apartmentId = 1,
                apartmentNumber = 12,
                apartmentFullAddress = "пер. Соборный 99, кв. 12",
                meters = dtoMeters
            ),
            ApartmentWithMeterGetDto(
                apartmentId = 2,
                apartmentNumber = 12,
                apartmentFullAddress = "ул. Малюгина 124, кв. 12",
                meters = dtoMeters
            )
        )

        val actual = meterMapper.mapListToDomain(dtoApartments)
        Assertions.assertEquals(expectedApartments, actual)
    }
}