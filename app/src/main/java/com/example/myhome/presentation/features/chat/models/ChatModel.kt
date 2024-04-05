package com.example.myhome.presentation.features.chat.models

import android.os.Bundle
import android.os.Parcel
import com.example.myhome.presentation.features.chat.MessageFormatter
import com.example.myhome.presentation.features.chat.converters.CombinedTimeConverter
import com.example.myhome.presentation.models.AdaptiveString
import com.example.myhome.presentation.models.ParcelableModel
import com.example.myhome.presentation.models.readDate
import com.example.myhome.presentation.models.writeDate
import com.example.myhome.presentation.utils.converters.CombinedDateConverter
import com.example.myhome.presentation.utils.pickers.CharPicker
import java.util.Date

data class ChatUiModel(
    override val id: String,
    val receiverName: String,
    val lastMessageText: String?,
    val lastMessageAt: Long?,
    val isMyMessageLast: Boolean,
    val countUnread: Int,
    val createdAt: Date
) : AdaptiveString, CombinedTimeConverter, CharPicker, MessageFormatter {
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
        return formatCountUnread(countUnread)
    }

    fun formatReceiverName(): String {
        return processString(receiverName)
    }
}

class ChatAddToGetParcelableModel(
    val id: String,
    val receiverName: String,
    val createdAt: Date
) : ParcelableModel(), CharPicker, CombinedDateConverter {
    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
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
        parcel.writeString(id)
        parcel.writeString(receiverName)
        parcel.writeDate(createdAt)
    }
}
