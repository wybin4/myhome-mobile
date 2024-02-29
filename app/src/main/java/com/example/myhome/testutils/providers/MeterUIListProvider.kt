package com.example.myhome.testutils.providers

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.utils.converters.MeterParcelableConverter
import com.example.myhome.utils.models.ApartmentUiModel
import com.example.myhome.utils.models.MeterGetToScanParcelableModel
import com.example.myhome.utils.models.MeterGetToUpdateParcelableModel
import com.example.myhome.utils.models.MeterListToGetParcelableModel
import com.example.myhome.utils.converters.MeterUiConverter
import com.example.myhome.utils.models.MeterUiModel
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

    fun getApartmentWithMeterList(): List<ApartmentWithMeterGetModel> {
        return apartmentList
    }

    fun getApartmentUiList(): List<ApartmentUiModel> {
        val selectedId = apartmentList.first().id
        return apartmentListToUi(apartmentList).map {
            it.setSelected(selectedId)
        }
    }

}
