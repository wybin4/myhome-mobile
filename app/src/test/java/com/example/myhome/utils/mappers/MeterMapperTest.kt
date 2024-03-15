package com.example.myhome.utils.mappers

import com.example.myhome.presentation.features.meter.mappers.MeterMapper
import com.example.myhome.presentation.testutils.providers.MeterUITestListProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class MeterMapperTest {

    private val mapper = MeterMapper()

    @Test
    fun `mapApartmentListToUi maps apartments correctly`() {
        val apartmentList = MeterUITestListProvider.getApartmentWithMeterList()
        val result = mapper.apartmentListToUi(apartmentList)

        assertEquals(apartmentList.size, result.size)
        result.forEach { apartmentUiModel ->
            assertEquals(false, apartmentUiModel.selected)
        }
    }

    @Test
    fun `mapApartmentWithMeterToUi maps meters correctly`() {
        val apartment = MeterUITestListProvider.getApartmentWithMeterList().first()
        val result = mapper.apartmentWithMeterToUi(apartment)

        assertEquals(apartment.meters.size, result.size)
        result.forEach { meterUiModel ->
            assertEquals(false, meterUiModel.isIssued)
        }
    }

    @Test
    fun `mapMeterUiToGetParcel maps meter UI model to parcel correctly`() {
        val meter = MeterUITestListProvider.getMeterUiList().first()
        val result = mapper.meterUiToGetParcel(meter)
        val expected = MeterUITestListProvider.getMeterParcelableGet(0)
        assertEquals(expected.id, result.id)
        assertEquals(expected.address, result.address)
    }

    @Test
    fun `mapMeterGetToScanParcel maps meter parcel to scan parcel correctly`() {
        val meter = MeterUITestListProvider.getMeterParcelableGet(0)
        val result = mapper.meterGetToScanParcel(meter, 0.0)
        val expected = MeterUITestListProvider.getMeterParcelableScan(0)
        assertEquals(expected.meterId, result.meterId)
        assertEquals(expected.address, result.address)
    }

    @Test
    fun `mapMeterGetToUpdateParcel maps meter parcel to update parcel correctly`() {
        val meter = MeterUITestListProvider.getMeterParcelableGet(0)
        val result = mapper.meterGetToUpdateParcel(meter)
        val expected = MeterUITestListProvider.getMeterParcelableUpdate(0)
        assertEquals(expected.meterId, result.meterId)
        assertEquals(expected.apartmentId, result.apartmentId)
    }
}
