package com.example.myhome.presentation.meter

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class MeterParcelableModel(
    val id: Int,
    val factoryNumber: String,
    val verifiedAt: Date,
    val issuedAt: Date,
    val address: String,
    val currentReading: String,
    val typeOfServiceName: String,
    val unitName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        factoryNumber = parcel.readString() ?: "",
        verifiedAt = Date(parcel.readLong()),
        issuedAt = Date(parcel.readLong()),
        address = parcel.readString() ?: "",
        currentReading = parcel.readString() ?: "—",
        typeOfServiceName = parcel.readString() ?: "",
        unitName = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(factoryNumber)
        parcel.writeLong(verifiedAt.time)
        parcel.writeLong(issuedAt.time)
        parcel.writeString(address)
        parcel.writeValue(currentReading)
        parcel.writeString(typeOfServiceName)
        parcel.writeString(unitName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MeterParcelableModel> {
        override fun createFromParcel(parcel: Parcel): MeterParcelableModel {
            return MeterParcelableModel(parcel)
        }

        override fun newArray(size: Int): Array<MeterParcelableModel?> {
            return arrayOfNulls(size)
        }
    }
}
