package com.example.myhome.presentation.features.chat.converters

import android.util.Log
import com.example.myhome.features.chat.ChatListItemResponse
import com.example.myhome.features.chat.ChatUserListItemResponse
import com.example.myhome.features.chat.MessageListItemResponse
import com.example.myhome.features.chat.MessageStatus
import com.example.myhome.models.UserRole
import com.example.myhome.presentation.features.chat.ChatUiModel

interface ChatUiConverter {
    private fun getReceiver(users: List<ChatUserListItemResponse>): ChatUserListItemResponse? {
        val currentUser = ChatUserListItemResponse(userId = 1, userRole = UserRole.Owner, name = "")
        return users.firstOrNull { it != currentUser }
    }

    private fun getLastMessage(messages: List<MessageListItemResponse>): MessageListItemResponse? {
        return messages.minByOrNull { it.createdAt }
    }

    private fun isMyMessageLast(message: MessageListItemResponse): Boolean {
        return message.sender.userId == 1 && message.sender.userRole == UserRole.Owner
    }

    private fun getCountUnread(messages: List<MessageListItemResponse>): Int {
        return messages.count { it.status == MessageStatus.Unread }
    }

    fun chatListToUi(chats: List<ChatListItemResponse>): List<ChatUiModel> {
        return chats.map {
            val receiver = getReceiver(it.users)
            val lastMessage = getLastMessage(it.messages)
            if (receiver != null) {
                if (lastMessage != null) {
                    ChatUiModel(
                        id = it.id,
                        receiverName = receiver.name,
                        lastMessageText = lastMessage.text,
                        lastMessageAt = lastMessage.formatCreatedAt(),
                        isMyMessageLast = isMyMessageLast(lastMessage),
                        countUnread = getCountUnread(it.messages)
                    )
                } else {
                    ChatUiModel(
                        id = it.id,
                        receiverName = receiver.name,
                        lastMessageText = null,
                        lastMessageAt = null,
                        isMyMessageLast = false,
                        countUnread = 0
                    )
                }
            } else {
                throw IllegalArgumentException("Receiver cannot be null")
            }
        }.sortedByDescending { it.lastMessageAt }
    }
}