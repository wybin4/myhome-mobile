package com.example.myhome.meter.mappers

import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.dtos.MeterListItemDto
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterListItemModel
import com.example.myhome.utils.converters.MeterConverter

class MeterRemoteMapper: MeterConverter {
    fun mapApartmentWithMeterListToDomain(apartments: List<ApartmentWithMeterGetDto>): List<ApartmentWithMeterGetModel> {
        return apartmentWithMeterListToDomain(apartments)
    }

    fun mapMeterListToDomain(meters: List<MeterListItemDto>): List<MeterListItemModel> {
        return meterListToDomain(meters)
    }
}
