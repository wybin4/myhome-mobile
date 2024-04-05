package com.example.myhome.presentation.features.charge.models

import android.os.Bundle
import android.os.Parcel
import com.example.myhome.features.charge.dtos.AmountChange
import com.example.myhome.presentation.features.charge.converters.MoneyConverter
import com.example.myhome.presentation.features.charge.converters.MonthYearConverter
import com.example.myhome.presentation.models.AdaptiveInt
import com.example.myhome.presentation.models.ParcelableModel
import com.example.myhome.presentation.utils.converters.PercentConverter
import com.example.myhome.presentation.utils.pickers.IconPicker
import java.util.Date

data class ChargeChartItem(
    val id: Float,
    val createdAt: Date,
    val amount: Float
): MonthYearConverter {
    fun formatCreatedAt(): String {
        return formatDate(createdAt)
    }
}

data class ChargeChartModel(
    val apartmentId: Int,
    val apartmentName: String,
    val charges: List<ChargeChartItem>
)

data class ChargeUiModel(
    override val id: Int,
    val apartmentName: String,
    val managementCompanyName: String,
    val managementCompanyCheckingAccount: String,
    val createdAt: Date,
    val outstandingDebt: Double,
    val originalDebt: Double,
    var percent: Double,
    val amountChange: AmountChange
) : AdaptiveInt, MoneyConverter, PercentConverter, IconPicker, MonthYearConverter {
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

    fun formatCreatedAt(): String {
        return formatDate(createdAt)
    }
}

class ChargeListToGetParcelableModel(
    val id: Int,
    val managementCompanyName: String,
    val managementCompanyCheckingAccount: String,
    val apartmentName: String,
    val periodName: String,
    val originalDebt: Double,
    val outstandingDebt: Double
) : ParcelableModel(), MoneyConverter {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        managementCompanyName = parcel.readString() ?: "",
        managementCompanyCheckingAccount = parcel.readString() ?: "",
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
        parcel.writeString(managementCompanyCheckingAccount)
        parcel.writeString(apartmentName)
        parcel.writeString(periodName)
        parcel.writeDouble(originalDebt)
        parcel.writeDouble(outstandingDebt)
    }
}

class ChargeGetToPayParcelableModel(
    val id: Int,
    val managementCompanyName: String,
    val managementCompanyCheckingAccount: String,
    val periodName: String,
    val outstandingDebt: Double
) : ParcelableModel(), MoneyConverter {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        managementCompanyName = parcel.readString() ?: "",
        managementCompanyCheckingAccount = parcel.readString() ?: "",
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
        parcel.writeString(managementCompanyCheckingAccount)
        parcel.writeString(periodName)
        parcel.writeDouble(outstandingDebt)
    }
}