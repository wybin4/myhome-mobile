package com.example.myhome.presentation.features.chat.converters

import com.example.myhome.features.chat.MessageStatus
import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.features.chat.dtos.ChatUserListItemResponse
import com.example.myhome.features.chat.dtos.MessageAddResponse
import com.example.myhome.features.chat.dtos.MessageListItemResponse
import com.example.myhome.models.UserRole
import com.example.myhome.presentation.features.chat.models.ChatUiModel
import com.example.myhome.presentation.features.chat.models.MessageCreatedAtUiModel
import com.example.myhome.presentation.features.chat.models.MessageState
import com.example.myhome.presentation.features.chat.models.MessageUiModel
import com.example.myhome.presentation.features.chat.models.ReceiverUiModel

interface ChatUiConverter {
    private fun isItMe(sender: ChatUserListItemResponse): Boolean {
        return sender.userId == 1 && sender.userRole == UserRole.Owner
    }

    fun chatListToUi(chats: List<ChatListItemResponse>): List<ChatUiModel> {
        return chats
            .map {
                if (it.lastMessage != null) {
                    ChatUiModel(
                        id = it.id,
                        receiverName = it.receiverName,
                        lastMessageText = it.lastMessage!!.text,
                        lastMessageAt = it.lastMessage!!.createdAt,
                        isMyMessageLast = isItMe(it.lastMessage!!.sender),
                        countUnread = it.countUnread,
                        createdAt = it.formatCreatedAt()
                    )
                } else {
                    ChatUiModel(
                        id = it.id,
                        receiverName = it.receiverName,
                        lastMessageText = null,
                        lastMessageAt = null,
                        isMyMessageLast = false,
                        countUnread = it.countUnread,
                        createdAt = it.formatCreatedAt()
                    )
                }
            }
            .sortedByDescending { it.lastMessageAt ?: it.createdAt.time }
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
                createdAt = formatCreatedAt(),
                messages = messages.map { message ->
                    MessageCreatedAtUiModel(
                        id = message.id,
                        text = message.text,
                        createdAt = message.createdAt,
                        isItMe = isItMe(message.sender),
                        messageState = MessageState.Success(message.status == MessageStatus.Read)
                    )
                }
            )
        }
    }

    fun messageAddToUi(message: MessageAddResponse): MessageCreatedAtUiModel {
        return message.run {
            MessageCreatedAtUiModel(
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