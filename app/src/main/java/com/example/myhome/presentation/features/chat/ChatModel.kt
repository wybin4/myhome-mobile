package com.example.myhome.presentation.features.chat

import com.example.myhome.presentation.features.chat.converters.CombinedTimeConverter
import com.example.myhome.presentation.models.Adaptive
import com.example.myhome.presentation.utils.pickers.CharPicker
import java.util.Date

data class ChatUiModel(
    override val id: Int,
    val receiverName: String,
    val lastMessageText: String?,
    val lastMessageAt: Date?,
    val isMyMessageLast: Boolean,
    val countUnread: Int
) : Adaptive, CombinedTimeConverter, CharPicker {
    fun formatMessageAt(): String {
        return if (lastMessageAt != null) {
            formatTime(lastMessageAt)
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