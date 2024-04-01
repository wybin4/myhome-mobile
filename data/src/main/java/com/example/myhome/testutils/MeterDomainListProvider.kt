package com.example.myhome.testutils

import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.features.meter.dtos.MeterExtendedListItemResponse
import com.example.myhome.features.meter.dtos.MeterListItemResponse
import com.example.myhome.features.meter.dtos.ReadingListItemResponse
import com.example.myhome.features.meter.models.MeterStatus
import com.example.myhome.testutils.DateDomainProvider.getDateString
import com.example.myhome.testutils.DateDomainProvider.notIssuedDateString

object MeterDomainTestListProvider {
    val apartmentList = listOf(
        ApartmentWithMeterListItemResponse(1, "пер. Соборный 99, кв. 12", 12, getMeterList()),
        ApartmentWithMeterListItemResponse(2, "ул. Малюгина 124, кв. 12", 12, emptyList())
    )

    val meterListItems = listOf(
        MeterExtendedListItemResponse(1, "", "", "", 1, "Газ", "", 1, 1,"пер. Соборный 99, кв. 12", MeterStatus.Active),
        MeterExtendedListItemResponse(2, "", "", "", 1, "ХВС", "", 1, 1,"пер. Соборный 99, кв. 12", MeterStatus.Active),
        MeterExtendedListItemResponse(3, "", "", "", 1, "ГВС", "", 1, 1,"пер. Соборный 99, кв. 12", MeterStatus.Active),
    )

    val readingList = listOf(
        ReadingListItemResponse(
            id = 4,
            readAt = getDateString(),
            consumption = 1.291,
            reading = 5.867
        ),
        ReadingListItemResponse(
            id = 3,
            readAt = getDateString(),
            consumption = 1.3,
            reading = 4.576
        ),
        ReadingListItemResponse(
            id = 2,
            readAt = getDateString(),
            consumption = 2.21,
            reading = 3.276
        ),
        ReadingListItemResponse(
            id = 1,
            readAt = getDateString(),
            consumption = 1.005,
            reading = 1.066
        )
    )

    fun getMeterList(): List<MeterListItemResponse> {
        val notIssuedDate = notIssuedDateString()

        return listOf(
            MeterListItemResponse(1, "12332132131231", getDateString(), getDateString(), 1, "Газ", "Gas",16.9, "м3"),
            MeterListItemResponse(2, "12332132131232", getDateString(), notIssuedDate, 2, "ХВС", "Water",0.0, "м3"),
            MeterListItemResponse(3, "12132132131232", getDateString(), notIssuedDate, 3, "ГВС", "Water",11.2, "гКал")
        )
    }

}
