package com.example.myhome.presentation.features.chat.models

import android.os.Bundle
import android.os.Parcel
import com.example.myhome.models.UnixTimeConverter
import com.example.myhome.presentation.features.charge.converters.MoneyConverter
import com.example.myhome.presentation.features.chat.converters.CombinedTimeConverter
import com.example.myhome.presentation.models.Adaptive
import com.example.myhome.presentation.models.ParcelableModel
import com.example.myhome.presentation.models.readDate
import com.example.myhome.presentation.models.writeDate
import com.example.myhome.presentation.utils.converters.CombinedDateConverter
import com.example.myhome.presentation.utils.pickers.CharPicker
import java.util.Date

data class ChatUiModel(
    override val id: Int,
    val receiverName: String,
    val lastMessageText: String?,
    val lastMessageAt: Long?,
    val isMyMessageLast: Boolean,
    val countUnread: Int,
    val createdAt: Date
) : Adaptive, CombinedTimeConverter, CharPicker {
    fun formatMessageAt(): String {
        return if (lastMessageAt != null) {
            val date = Date(lastMessageAt)
            formatTime(date)
        } else ""
    }

    fun formatMessageText(): String? {
        return if (isMyMessageLast) {
            "Вы: $lastMessageText"
        } else lastMessageText
    }

    fun formatCountUnread(): String {
        return if (countUnread < 100) {
            countUnread.toString()
        } else "99+"
    }

    fun formatReceiverName(): String {
        return processString(receiverName)
    }
}

class ChatAddToGetParcelableModel(
    val id: Int,
    val receiverName: String,
    val createdAt: Date
) : ParcelableModel(), CharPicker, CombinedDateConverter {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        receiverName = parcel.readString() ?: "",
        createdAt = parcel.readDate() ?: Date()
    )

    fun formatReceiverName(): String {
        return processString(receiverName)
    }

    fun formatCreatedAt(): String {
        return "Чат создан ${formatDate(createdAt)}"
    }

    fun toBundle(): Bundle {
        return Bundle().apply {
            putParcelable("chat", this@ChatAddToGetParcelableModel)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(receiverName)
        parcel.writeDate(createdAt)
    }
}
