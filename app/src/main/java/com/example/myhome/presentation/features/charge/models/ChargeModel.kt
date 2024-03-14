package com.example.myhome.presentation.features.charge.models

import android.os.Bundle
import android.os.Parcel
import com.example.myhome.presentation.features.charge.converters.MoneyConverter
import com.example.myhome.presentation.models.Adaptive
import com.example.myhome.presentation.models.ParcelableModel
import com.example.myhome.presentation.utils.converters.PercentConverter
import com.example.myhome.presentation.utils.pickers.IconPicker

enum class AmountChange {
    Positive, Negative, None
}

data class ChargeUiModel(
    override val id: Int,
    val apartmentName: String,
    val managementCompanyName: String,
    val periodName: String,
    val outstandingDebt: Double,
    val originalDebt: Double,
    var percent: Double,
    val amountChange: AmountChange
) : Adaptive, MoneyConverter, PercentConverter, IconPicker {
    fun formatOriginalDebt(): String {
        return formatDouble2F(originalDebt)
    }

    fun formatPercent(): String {
        return formatDouble1F(percent)
    }

    fun getAmountChangeIcon(): Int? {
        return getAmountChangeIcon(amountChange)
    }

    fun hasOutstandingDebt(): Boolean {
        return outstandingDebt > 0
    }
}

class ChargeListToGetParcelableModel(
    val id: Int,
    val managementCompanyName: String,
    val apartmentName: String,
    val periodName: String,
    val originalDebt: Double,
    val outstandingDebt: Double
) : ParcelableModel(), MoneyConverter {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        managementCompanyName = parcel.readString() ?: "",
        apartmentName = parcel.readString() ?: "",
        periodName = parcel.readString() ?: "",
        originalDebt = parcel.readDouble(),
        outstandingDebt = parcel.readDouble()
    )

    fun formatOriginalDebt(): String {
        return formatDouble2F(originalDebt)
    }

    fun formatOutstandingDebt(): String {
        return formatDouble2F(outstandingDebt)
    }

    fun toBundle(): Bundle {
        return Bundle().apply {
            putParcelable("charge", this@ChargeListToGetParcelableModel)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(managementCompanyName)
        parcel.writeString(apartmentName)
        parcel.writeString(periodName)
        parcel.writeDouble(originalDebt)
        parcel.writeDouble(outstandingDebt)
    }
}

class ChargeGetToPayParcelableModel(
    val id: Int,
    val managementCompanyName: String,
    val periodName: String,
    val outstandingDebt: Double
) : ParcelableModel(), MoneyConverter {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        managementCompanyName = parcel.readString() ?: "",
        periodName = parcel.readString() ?: "",
        outstandingDebt = parcel.readDouble()
    )

    fun toBundle(): Bundle {
        return Bundle().apply {
            putParcelable("charge", this@ChargeGetToPayParcelableModel)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(managementCompanyName)
        parcel.writeString(periodName)
        parcel.writeDouble(outstandingDebt)
    }
}