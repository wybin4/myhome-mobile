package com.example.myhome.meter.mappers

import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.utils.converters.MeterConverter

class MeterRemoteMapper: MeterConverter {
    fun mapListToDomain(apartments: List<ApartmentWithMeterGetDto>): List<ApartmentWithMeterGetModel> {
        return meterListToDomain(apartments)
    }
}
