package com.example.myhome.testutils.providers

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import com.example.myhome.features.meter.models.MeterListItemModel
import com.example.myhome.features.meter.models.ReadingListItemModel
import com.example.myhome.testutils.providers.DateDomainProvider.date
import java.util.Calendar
import java.util.Date

object MeterDomainTestListProvider {
    val apartmentList = listOf(
        ApartmentWithMeterListItemModel(1, "пер. Соборный 99, кв. 12", getMeterList()),
        ApartmentWithMeterListItemModel(2, "ул. Малюгина 124, кв. 12", emptyList())
    )

    val meterListItems = listOf(
        MeterListItemExtendedModel(1, "Газ", 1, "пер. Соборный 99, кв. 12"),
        MeterListItemExtendedModel(2, "ХВС", 1, "пер. Соборный 99, кв. 12"),
        MeterListItemExtendedModel(3, "ГВС", 1, "пер. Соборный 99, кв. 12"),
    )

    val readingList = listOf(
        ReadingListItemModel(
            id = 4,
            readAt = date,
            consumption = 1.291,
            reading = 5.867
        ),
        ReadingListItemModel(
            id = 3,
            readAt = date,
            consumption = 1.3,
            reading = 4.576
        ),
        ReadingListItemModel(
            id = 2,
            readAt = date,
            consumption = 2.21,
            reading = 3.276
        ),
        ReadingListItemModel(
            id = 1,
            readAt = date,
            consumption = 1.005,
            reading = 1.066
        )
    )

    fun getMeterList(): List<MeterListItemModel> {
        val notIssuedDate = notIssuedDate()

        return listOf(
            MeterListItemModel(1, "12332132131231", date, date, 1, "Газ", "Gas",16.9, "м3"),
            MeterListItemModel(2, "12332132131232", date, notIssuedDate, 2, "ХВС", "Water",null, "м3"),
            MeterListItemModel(3, "12132132131232", date, notIssuedDate, 3, "ГВС", "Water",11.2, "гКал")
        )
    }

    private fun notIssuedDate(): Date {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.YEAR, 1)
        return currentDate.time ?: Date()
    }
}
