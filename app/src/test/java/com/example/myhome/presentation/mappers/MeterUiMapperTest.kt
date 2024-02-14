package com.example.myhome.presentation.mappers

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterGetModel
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Date

class MeterUiMapperTest {

    private val mapper = MeterUiMapper()
    private val date = Date()
    private val address = "Мой адрес - Не дом и не улица"

    @Test
    fun `mapApartmentListToUi maps apartments correctly`() {
        val apartmentList = listOf(
            ApartmentWithMeterGetModel(1, address, emptyList()),
            ApartmentWithMeterGetModel(2, address, emptyList())
        )

        val result = mapper.mapApartmentListToUi(apartmentList)

        assertEquals(apartmentList.size, result.size)
        result.forEach { apartmentUiModel ->
            assertEquals(false, apartmentUiModel.selected)
        }
    }

    @Test
    fun `mapMeterListToUi maps meters correctly`() {
        val apartment = ApartmentWithMeterGetModel(
            id = 1,
            address = address,
            meters = listOf(
                MeterGetModel(1, "2135746352344", date, date, 3, "ХВС", 12.2, "м3"),
                MeterGetModel(2, "2135746352342", date, date, 5, "Газ", 15.5, "м3")
            )
        )

        val result = mapper.mapMeterListToUi(apartment)

        assertEquals(apartment.meters.size, result.size)
        result.forEach { meterUiModel ->
            assertEquals(false, meterUiModel.isIssued)
        }
    }
}