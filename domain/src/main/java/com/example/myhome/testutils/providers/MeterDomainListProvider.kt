package com.example.myhome.testutils.providers

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.models.ReadingGetModel
import com.example.myhome.testutils.providers.DateDomainProvider.date
import java.util.Calendar
import java.util.Date

public object MeterDomainTestListProvider {
    val apartmentList = listOf(
        ApartmentWithMeterGetModel(1, "пер. Соборный 99, кв. 12", getMeterList()),
        ApartmentWithMeterGetModel(2, "ул. Малюгина 124, кв. 12", emptyList())
    )

    val readingList = listOf(
        ReadingGetModel(
            id = 4,
            readAt = date,
            consumption = 1.291,
            reading = 5.867
        ),
        ReadingGetModel(
            id = 3,
            readAt = date,
            consumption = 1.3,
            reading = 4.576
        ),
        ReadingGetModel(
            id = 2,
            readAt = date,
            consumption = 2.21,
            reading = 3.276
        ),
        ReadingGetModel(
            id = 1,
            readAt = date,
            consumption = 1.005,
            reading = 1.066
        )
    )

    fun getMeterList(): List<MeterGetModel> {
        val notIssuedDate = notIssuedDate()

        return listOf(
            MeterGetModel(1, "12332132131231", date, date, 1, "Газ", "Gas",16.9, "м3"),
            MeterGetModel(2, "12332132131232", date, notIssuedDate, 2, "ХВС", "Water",null, "м3"),
            MeterGetModel(3, "12132132131232", date, notIssuedDate, 3, "ГВС", "Water",11.2, "гКал")
        )
    }

    private fun notIssuedDate(): Date {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.YEAR, 1)
        return currentDate.time ?: Date()
    }
}
