package com.example.myhome.presentation.features.chat.converters

import com.example.myhome.features.chat.ChatListItemResponse
import com.example.myhome.features.chat.ChatUserListItemResponse
import com.example.myhome.features.chat.MessageListItemResponse
import com.example.myhome.models.UserRole
import com.example.myhome.presentation.features.chat.models.ChatUiModel
import com.example.myhome.presentation.features.chat.models.ReceiverUiModel

interface ChatUiConverter {
    private fun isMyMessageLast(message: MessageListItemResponse): Boolean {
        return message.sender.userId == 1 && message.sender.userRole == UserRole.Owner
    }

    fun chatListToUi(chats: List<ChatListItemResponse>): List<ChatUiModel> {
        return chats
            .filter { it.lastMessage != null }
            .map {
                ChatUiModel(
                    id = it.id,
                    receiverName = it.receiverName,
                    lastMessageText = it.lastMessage!!.text,
                    lastMessageAt = it.lastMessage!!.formatCreatedAt(),
                    isMyMessageLast = isMyMessageLast(it.lastMessage!!),
                    countUnread = it.countUnread
                )
            }
            .sortedByDescending { it.lastMessageAt }
    }

    fun receiverListToUi(receivers: List<ChatUserListItemResponse>): List<ReceiverUiModel> {
        return receivers.map {
            ReceiverUiModel(
                id = it.userId,
                role = it.userRole,
                name = it.name
            )
        }
    }
}