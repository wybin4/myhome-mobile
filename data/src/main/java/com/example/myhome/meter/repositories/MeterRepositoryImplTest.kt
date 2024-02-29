package com.example.myhome.meter.repositories

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.models.MeterListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date

class MeterRepositoryTest: MeterRepository {
    val date = Date()

    private val meterList = listOf(
        MeterGetModel(1, "12332132131231", date, date, 1, "Газ", "Gas",16.9, "м3"),
        MeterGetModel(2, "12332132131232", date, date, 2, "ХВС", "Water",12.0, "м3")
    )

    private val apartmentList = listOf(
        ApartmentWithMeterGetModel(1, "пер. Соборный 99, кв. 12", meterList),
        ApartmentWithMeterGetModel(2, "ул. Малюгина 124, кв. 12", emptyList())
    )

    override fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterGetModel>> {
        return flowOf(apartmentList)
    }

    override fun listMeter(): Flow<List<MeterListItemModel>> {
        TODO("Not yet implemented")
    }
}
