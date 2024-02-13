package com.example.myhome.presentation.meter.models

import com.example.myhome.R
import android.os.Parcel
import android.os.Parcelable
import com.example.myhome.common.models.Adaptive
import com.example.myhome.common.models.DateConverter
import java.util.Calendar
import java.util.Date


data class MeterUiModel(
    override val id: Int,
    val factoryNumber: String,
    val verifiedAt: Date,
    val issuedAt: Date,
    val apartmentId: Int,
    val address: String,
    val currentReading: Double?,
    val typeOfServiceName: String,
    val unitName: String,
    var isIssued: Boolean
) : Adaptive, DateConverter {

    fun formatIssuedAt(): String {
        return formatDate(issuedAt)
    }
    fun formatVerifiedAt(): String {
        return formatDate(verifiedAt)
    }
    fun setIsIssued(): MeterUiModel {
        val threeDaysLater = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 3)
        }.time
        isIssued = issuedAt.before(threeDaysLater)
        return this
    }
}

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

class MeterListToGetParcelableModel(
    val id: Int,
    val factoryNumber: String,
    val verifiedAt: Date,
    val issuedAt: Date,
    val isIssued: Boolean,
    val apartmentId: Int,
    val address: String,
    val currentReading: String,
    val typeOfServiceName: String,
    val unitName: String
) : ParcelableModel(), DateConverter {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        apartmentId = parcel.readInt(),
        factoryNumber = parcel.readString() ?: "",
        verifiedAt = Date(parcel.readLong()),
        issuedAt = Date(parcel.readLong()),
        isIssued = parcel.readInt() != 0,
        address = parcel.readString() ?: "",
        currentReading = parcel.readString() ?: "—",
        typeOfServiceName = parcel.readString() ?: "",
        unitName = parcel.readString() ?: ""
    )

    fun formatIssuedAt(): String {
        return formatDate(issuedAt)
    }
    fun formatVerifiedAt(): String {
        return formatDate(verifiedAt)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(factoryNumber)
        parcel.writeLong(verifiedAt.time)
        parcel.writeLong(issuedAt.time)
        parcel.writeInt(if (isIssued) 1 else 0)
        parcel.writeInt(id)
        parcel.writeString(address)
        parcel.writeString(currentReading)
        parcel.writeString(typeOfServiceName)
        parcel.writeString(unitName)
    }

}

class MeterGetToUpdateParcelableModel(
    val meterId: Int,
    val meterName: String,
    val apartmentId: Int
) : ParcelableModel(), DateConverter {
    constructor(parcel: Parcel) : this(
        meterId = parcel.readInt(),
        meterName = parcel.readString() ?: "",
        apartmentId = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(meterId)
        parcel.writeInt(apartmentId)
        parcel.writeString(meterName)
    }

}
