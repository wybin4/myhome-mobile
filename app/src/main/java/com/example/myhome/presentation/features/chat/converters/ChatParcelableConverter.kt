package com.example.myhome.presentation.features.chat.converters

import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.presentation.features.chat.models.ChatAddToGetParcelableModel
import com.example.myhome.presentation.features.chat.models.ChatUiModel

interface ChatParcelableConverter {
    fun chatAddToGetParcel(chat: ChatListItemResponse): ChatAddToGetParcelableModel {
        return ChatAddToGetParcelableModel(
            id = chat.id,
            receiverName = chat.receiverName,
            createdAt = chat.formatCreatedAt()
        )
    }

    fun chatListToGetParcel(chat: ChatUiModel): ChatAddToGetParcelableModel {
        return ChatAddToGetParcelableModel(
            id = chat.id,
            receiverName = chat.receiverName,
            createdAt = chat.createdAt
        )
    }
}