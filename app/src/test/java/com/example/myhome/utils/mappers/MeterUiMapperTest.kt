package com.example.myhome.utils.mappers

import com.example.myhome.testutils.providers.MeterUITestListProvider.getApartmentWithMeterList
import com.example.myhome.utils.mappers.MeterUiMapper
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MeterUiMapperTest {

    private val mapper = MeterUiMapper()
    private val apartmentList = getApartmentWithMeterList()

    @Test
    fun `mapApartmentListToUi maps apartments correctly`() {
        val result = mapper.mapApartmentListToUi(apartmentList)

        assertEquals(apartmentList.size, result.size)
        result.forEach { apartmentUiModel ->
            assertEquals(false, apartmentUiModel.selected)
        }
    }

    @Test
    fun `mapMeterListToUi maps meters correctly`() {
        val apartment = apartmentList.first()

        val result = mapper.mapMeterListToUi(apartment)

        assertEquals(apartment.meters.size, result.size)
        result.forEach { meterUiModel ->
            assertEquals(false, meterUiModel.isIssued)
        }
    }
}