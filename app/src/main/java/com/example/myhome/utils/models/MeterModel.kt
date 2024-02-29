package com.example.myhome.utils.models

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.example.myhome.common.models.Adaptive
import com.example.myhome.common.models.DateConverter
import com.example.myhome.common.models.Identifiable
import com.example.myhome.utils.converters.DoubleNullableConverter
import com.example.myhome.utils.pickers.IconPicker
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
    val typeOfServiceEngName: String,
    val unitName: String,
    var isIssued: Boolean
) : Adaptive, DateConverter, DoubleNullableConverter, IconPicker {
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
    fun formatCurrentReading(): String {
        return x2doubleToString(currentReading, unitName)
    }
    fun getIcon(): Int? {
        return getMeterIcon(typeOfServiceEngName)
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
    val currentReading: Double?,
    val typeOfServiceName: String,
    val typeOfServiceEngName: String,
    val unitName: String
) : ParcelableModel(), DateConverter, DoubleNullableConverter, IconPicker {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        factoryNumber = parcel.readString() ?: "",
        verifiedAt = Date(parcel.readLong()),
        issuedAt = Date(parcel.readLong()),
        isIssued = parcel.readInt() != 0,
        apartmentId = parcel.readInt(),
        address = parcel.readString() ?: "",
        currentReading = parcel.readDouble(),
        typeOfServiceName = parcel.readString() ?: "",
        typeOfServiceEngName = parcel.readString() ?: "",
        unitName = parcel.readString() ?: ""
    )

    fun formatIssuedAt(): String {
        return formatDate(issuedAt)
    }
    fun formatVerifiedAt(): String {
        return formatDate(verifiedAt)
    }
    fun formatCurrentReading(): String {
        return doubleToString(currentReading)
    }
    fun getIcon(): Int? {
        return getMeterIcon(typeOfServiceEngName)
    }

    fun toBundle(): Bundle {
        return Bundle().apply {
            putParcelable("meter", this@MeterListToGetParcelableModel)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(factoryNumber)
        parcel.writeLong(verifiedAt.time)
        parcel.writeLong(issuedAt.time)
        parcel.writeInt(if (isIssued) 1 else 0)
        parcel.writeInt(apartmentId)
        parcel.writeString(address)
        parcel.writeDouble(currentReading ?: Double.NaN)
        parcel.writeString(typeOfServiceName)
        parcel.writeString(typeOfServiceEngName)
        parcel.writeString(unitName)
    }
}

class MeterGetToScanParcelableModel(
    val meterId: Int,
    val address: String,
    val previousReading: Double,
    val typeOfServiceName: String,
    val typeOfServiceEngName: String,
    val unitName: String
) : ParcelableModel(), IconPicker {
    constructor(parcel: Parcel) : this(
        meterId = parcel.readInt(),
        address = parcel.readString() ?: "",
        previousReading = parcel.readDouble(),
        typeOfServiceName = parcel.readString() ?: "",
        typeOfServiceEngName = parcel.readString() ?: "",
        unitName = parcel.readString() ?: ""
    )

    fun getIcon(): Int? {
        return getMeterIcon(typeOfServiceEngName)
    }

    fun toBundle(): Bundle {
        return Bundle().apply {
            putParcelable("meter", this@MeterGetToScanParcelableModel)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(meterId)
        parcel.writeString(address)
        parcel.writeDouble(previousReading)
        parcel.writeString(typeOfServiceName)
        parcel.writeString(typeOfServiceEngName)
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

    fun toBundle(): Bundle {
        return Bundle().apply {
            putParcelable("meter", this@MeterGetToUpdateParcelableModel)
        }
    }

}

class MeterListItemUiModel(
    override val id: Int,
    val subscriberId: Int,
    override val name: String
): Identifiable