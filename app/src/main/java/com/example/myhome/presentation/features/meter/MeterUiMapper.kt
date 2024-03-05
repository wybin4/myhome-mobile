package com.example.myhome.presentation.features.meter

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import javax.inject.Inject

class MeterUiMapper @Inject constructor(): MeterParcelableConverter, MeterUiConverter {
    fun mapApartmentListToUi(apartments: List<ApartmentWithMeterListItemModel>): List<ApartmentUiModel> {
        return apartmentListToUi(apartments)
    }

    fun mapApartmentWithMeterToUi(apartment: ApartmentWithMeterListItemModel): List<MeterUiModel> {
        return apartmentWithMeterToUi(apartment)
    }

    fun mapMeterListToUi(meters: List<MeterListItemExtendedModel>): List<MeterExtendedUiModel> {
        return meterListToUi(meters)
    }

    fun mapMeterUiToGetParcel(meter: MeterUiModel): MeterListToGetParcelableModel {
        return meterUiToGetParcel(meter)
    }

    fun mapMeterGetToScanParcel(meter: MeterListToGetParcelableModel, prevReading: Double): MeterGetToScanParcelableModel {
        return meterGetToScanParcel(meter, prevReading)
    }

    fun mapMeterGetToUpdateParcel(meter: MeterListToGetParcelableModel): MeterGetToUpdateParcelableModel {
        return meterGetToUpdateParcel(meter)
    }
}