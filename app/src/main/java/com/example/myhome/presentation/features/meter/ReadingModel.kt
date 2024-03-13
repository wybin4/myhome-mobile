package com.example.myhome.presentation.features.meter

import android.os.Parcel
import com.example.myhome.models.DateConverter
import com.example.myhome.presentation.models.Adaptive
import com.example.myhome.presentation.models.ParcelableModel
import java.util.Date

data class ReadingUiModel(
    override val id: Int,
    val reading: Double,
    val consumption: Double,
    val readAt: Date,
    val unitName: String
): Adaptive, DateConverter {
    fun formattedDate(): String {
        return formatDate(readAt)
    }
}

class ReadingScanToGetParcelableModel(
    val id: Int,
    val reading: Double,
    val consumption: Double,
    val readAt: Date
) : ParcelableModel(), DateConverter {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        reading = parcel.readDouble(),
        consumption = parcel.readDouble(),
        readAt = Date(parcel.readLong()),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeDouble(reading)
        parcel.writeDouble(consumption)
        parcel.writeLong(readAt.time)
    }

}