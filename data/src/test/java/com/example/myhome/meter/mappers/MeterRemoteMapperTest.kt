package com.example.myhome.meter.mappers

import com.example.myhome.base.mappers.DateMapper
import com.example.myhome.meter.dtos.MeterGetDto
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
    fun `map correct meter`() {
        val dto = MeterGetDto(
            id = 1,
            factoryNumber = "1238949637983",
            verifiedAt = dateString,
            issuedAt = dateString,
            apartmentId = 101,
            typeOfServiceId = 1,
            currentReading = 43.4,
            typeOfServiceName = "Электроэнергия",
            unitName = "кВт"
        )
        val expected = MeterGetModel(
            id = 1,
            factoryNumber = "1238949637983",
            verifiedAt = date,
            issuedAt = date,
            apartmentId = 101,
            typeOfServiceId = 1,
            currentReading = 43.4,
            typeOfServiceName = "Электроэнергия",
            unitName = "кВт"
        )
        val actual = meterMapper.mapToDomain(dto)
        Assertions.assertEquals(expected, actual)
    }
    @Test
    fun `map correct list`() {
        val dto = listOf(
            MeterGetDto(
                id = 1,
                factoryNumber = "1238949637983",
                verifiedAt = dateString,
                issuedAt = dateString,
                apartmentId = 101,
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
                apartmentId = 101,
                typeOfServiceId = 1,
                currentReading = 43.4,
                typeOfServiceName = "ГВС",
                unitName = "м3"
            )
        )
        val expected = listOf(
            MeterGetModel(
                id = 1,
                factoryNumber = "1238949637983",
                verifiedAt = date,
                issuedAt = date,
                apartmentId = 101,
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
                apartmentId = 101,
                typeOfServiceId = 1,
                currentReading = 43.4,
                typeOfServiceName = "ГВС",
                unitName = "м3"
            )
        )
        val actual = meterMapper.mapListToDomain(dto)
        Assertions.assertEquals(expected, actual)
    }
}