package com.example.myhome.presentation.testutils.providers

import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.presentation.features.meter.ApartmentUiModel
import com.example.myhome.presentation.features.meter.MeterGetToScanParcelableModel
import com.example.myhome.presentation.features.meter.MeterGetToUpdateParcelableModel
import com.example.myhome.presentation.features.meter.MeterListToGetParcelableModel
import com.example.myhome.presentation.features.meter.MeterUiModel
import com.example.myhome.presentation.features.meter.converters.MeterParcelableConverter
import com.example.myhome.presentation.features.meter.converters.MeterUiConverter
import com.example.myhome.testutils.MeterDomainTestListProvider.apartmentList
import com.example.myhome.testutils.MeterDomainTestListProvider.readingList

object MeterUITestListProvider: MeterParcelableConverter, MeterUiConverter {
    fun getMeterUiList(): List<MeterUiModel> {
        val meters = apartmentWithMeterToUi(apartmentList[0])
        return meters.map { it.setIsIssued() }
    }

    fun getMeterParcelableGet(id: Int): MeterListToGetParcelableModel {
        val meter = getMeterUiList()[id]
        return meterUiToGetParcel(meter)
    }

    fun getMeterParcelableUpdate(id: Int): MeterGetToUpdateParcelableModel {
        return meterGetToUpdateParcel(getMeterParcelableGet(id))
    }

    fun getMeterParcelableScan(id: Int): MeterGetToScanParcelableModel {
        val prevReading = readingList.first().reading
        return meterGetToScanParcel(getMeterParcelableGet(id), prevReading)
    }

    fun getApartmentWithMeterList(): List<ApartmentWithMeterListItemResponse> {
        return apartmentList
    }

    fun getApartmentUiList(): List<ApartmentUiModel> {
        val selectedId = apartmentList.first().apartmentId
        return apartmentListToUi(apartmentList).map {
            it.setSelected(selectedId)
        }
    }

}
