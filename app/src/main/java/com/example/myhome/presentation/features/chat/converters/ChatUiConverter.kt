package com.example.myhome.presentation.features.chat.converters

import com.example.myhome.features.chat.MessageStatus
import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.features.chat.dtos.ChatUserListItemResponse
import com.example.myhome.features.chat.dtos.MessageListItemResponse
import com.example.myhome.models.UserRole
import com.example.myhome.presentation.features.chat.models.ChatUiModel
import com.example.myhome.presentation.features.chat.models.MessageState
import com.example.myhome.presentation.features.chat.models.MessageUiModel
import com.example.myhome.presentation.features.chat.models.ReceiverUiModel

interface ChatUiConverter {
    private fun isItMe(sender: ChatUserListItemResponse): Boolean {
        return sender.userId == 1 && sender.userRole == UserRole.Owner
    }

    fun chatListToUi(chats: List<ChatListItemResponse>): List<ChatUiModel> {
        return chats
            .filter { it.lastMessage != null }
            .map {
                ChatUiModel(
                    id = it.id,
                    receiverName = it.receiverName,
                    lastMessageText = it.lastMessage!!.text,
                    lastMessageAt = it.lastMessage!!.createdAt,
                    isMyMessageLast = isItMe(it.lastMessage!!.sender),
                    countUnread = it.countUnread,
                    createdAt = it.formatCreatedAt()
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

    fun messageToUi(message: MessageListItemResponse): MessageUiModel {
        return message.run {
            MessageUiModel(
                id = id,
                text = text,
                createdAt = createdAt,
                isItMe = isItMe(sender),
                messageState = MessageState.Success(status == MessageStatus.Read)
            )
        }
    }

    fun messageListToUi(messages: List<MessageListItemResponse>): List<MessageUiModel> {
        return messages.map { messageToUi(it) }
    }
}