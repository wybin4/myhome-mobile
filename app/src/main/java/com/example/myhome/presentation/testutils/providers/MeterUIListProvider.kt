package com.example.myhome.presentation.testutils.providers

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.presentation.features.meter.ApartmentUiModel
import com.example.myhome.presentation.features.meter.MeterGetToScanParcelableModel
import com.example.myhome.presentation.features.meter.MeterGetToUpdateParcelableModel
import com.example.myhome.presentation.features.meter.MeterListToGetParcelableModel
import com.example.myhome.presentation.features.meter.MeterUiModel
import com.example.myhome.presentation.features.meter.MeterParcelableConverter
import com.example.myhome.presentation.features.meter.MeterUiConverter
import com.example.myhome.testutils.providers.MeterDomainTestListProvider.apartmentList
import com.example.myhome.testutils.providers.MeterDomainTestListProvider.readingList

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

    fun getApartmentWithMeterList(): List<ApartmentWithMeterListItemModel> {
        return apartmentList
    }

    fun getApartmentUiList(): List<ApartmentUiModel> {
        val selectedId = apartmentList.first().id
        return apartmentListToUi(apartmentList).map {
            it.setSelected(selectedId)
        }
    }

}
