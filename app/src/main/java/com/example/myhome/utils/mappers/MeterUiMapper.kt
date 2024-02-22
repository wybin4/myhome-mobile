package com.example.myhome.utils.mappers

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.utils.converters.MeterUiConverter
import com.example.myhome.utils.converters.MeterParcelableConverter
import com.example.myhome.utils.models.ApartmentUiModel
import com.example.myhome.utils.models.MeterGetToScanParcelableModel
import com.example.myhome.utils.models.MeterGetToUpdateParcelableModel
import com.example.myhome.utils.models.MeterListToGetParcelableModel
import com.example.myhome.utils.models.MeterUiModel
import javax.inject.Inject

class MeterUiMapper @Inject constructor(): MeterParcelableConverter, MeterUiConverter {
    fun mapApartmentListToUi(apartments: List<ApartmentWithMeterGetModel>): List<ApartmentUiModel> {
        return apartmentListToUi(apartments)
    }

    fun mapMeterListToUi(apartment: ApartmentWithMeterGetModel): List<MeterUiModel> {
        return meterListToUi(apartment)
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