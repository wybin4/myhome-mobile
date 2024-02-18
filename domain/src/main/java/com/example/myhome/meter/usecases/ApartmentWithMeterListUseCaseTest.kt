package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterGetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Calendar
import java.util.Date

class ApartmentWithMeterListUseCaseTest : ApartmentWithMeterListUseCase {
    val date = Date()
    val notIssuedDate = notIssuedDate()

    private fun notIssuedDate(): Date {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.YEAR, 1)
        return currentDate.time ?: date
    }

    private val meterList = listOf(
        MeterGetModel(1, "12332132131231", date, date, 1, "Газ", "Gas",16.9, "м3"),
        MeterGetModel(2, "12332132131232", date, notIssuedDate, 2, "ХВС", "Water",12.0, "м3"),
        MeterGetModel(3, "12132132131232", date, notIssuedDate, 3, "ГВС", "Water",11.2, "гКал")
    )

    private val apartmentList = listOf(
        ApartmentWithMeterGetModel(1, "пер. Соборный 99, кв. 12", meterList),
        ApartmentWithMeterGetModel(2, "ул. Малюгина 124, кв. 12", emptyList())
    )
    override operator fun invoke(): Flow<List<ApartmentWithMeterGetModel>> {
        return flowOf(apartmentList)
    }
}
