package com.example.myhome.utils.converters

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterListItemModel
import com.example.myhome.utils.models.ApartmentUiModel
import com.example.myhome.utils.models.MeterGetToScanParcelableModel
import com.example.myhome.utils.models.MeterGetToUpdateParcelableModel
import com.example.myhome.utils.models.MeterListItemUiModel
import com.example.myhome.utils.models.MeterListToGetParcelableModel
import com.example.myhome.utils.models.MeterUiModel

interface MeterParcelableConverter {
    fun meterUiToGetParcel(meter: MeterUiModel): MeterListToGetParcelableModel {
        return MeterListToGetParcelableModel(
            id = meter.id,
            factoryNumber = meter.factoryNumber,
            verifiedAt = meter.verifiedAt,
            issuedAt = meter.issuedAt,
            isIssued = meter.isIssued,
            apartmentId = meter.apartmentId,
            address = meter.address,
            currentReading = meter.currentReading,
            typeOfServiceName = meter.typeOfServiceName,
            typeOfServiceEngName = meter.typeOfServiceEngName,
            unitName = meter.unitName
        )
    }

    fun meterGetToScanParcel(meter: MeterListToGetParcelableModel, prevReading: Double): MeterGetToScanParcelableModel {
        return MeterGetToScanParcelableModel(
            meterId = meter.id,
            address = meter.address,
            previousReading = prevReading,
            typeOfServiceName = meter.typeOfServiceName,
            typeOfServiceEngName = meter.typeOfServiceEngName,
            unitName = meter.unitName
        )
    }

    fun meterGetToUpdateParcel(meter: MeterListToGetParcelableModel): MeterGetToUpdateParcelableModel {
        return MeterGetToUpdateParcelableModel(
            meterId = meter.id,
            meterName = meter.address + ", " + meter.typeOfServiceName,
            apartmentId = meter.apartmentId
        )
    }
}

interface MeterUiConverter {
    fun apartmentWithMeterToUi(apartment: ApartmentWithMeterGetModel): List<MeterUiModel> {
        return apartment.meters.map { meter ->
            MeterUiModel(
                id = meter.id,
                factoryNumber = meter.factoryNumber,
                verifiedAt = meter.verifiedAt,
                issuedAt = meter.issuedAt,
                currentReading = meter.currentReading,
                typeOfServiceName = meter.typeOfServiceName,
                typeOfServiceEngName = meter.typeOfServiceEngName,
                unitName = meter.unitName,
                apartmentId = apartment.id,
                address = apartment.address,
                isIssued = false
            )
        }
    }

    fun meterListToUi(meters: List<MeterListItemModel>): List<MeterListItemUiModel> {
        return meters.map { meter ->
            MeterListItemUiModel(
                id = meter.id,
                subscriberId = meter.subscriberId,
                name = "${meter.address}, ИПУ ${meter.typeOfServiceName}"
            )
        }
    }

    fun apartmentListToUi(apartments: List<ApartmentWithMeterGetModel>): List<ApartmentUiModel> {
        return apartments.map { apartment ->
            ApartmentUiModel(
                id = apartment.id,
                name = apartment.address,
                selected = false
            )
        }
    }
}