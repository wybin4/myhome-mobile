package com.example.myhome.presentation.models

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

open class ParcelableModel() : Parcelable {
    constructor(parcel: Parcel) : this()

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ParcelableModel> = object : Parcelable.Creator<ParcelableModel> {
            override fun createFromParcel(parcel: Parcel): ParcelableModel {
                return ParcelableModel()
            }

            override fun newArray(size: Int): Array<ParcelableModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}

fun Parcel.readDate(): Date? {
    return readLong().let { time ->
        if (time != 0L) {
            Date(time)
        } else {
            null
        }
    }
}

fun Parcel.writeDate(date: Date?) {
    writeLong(date?.time ?: 0L)
}
