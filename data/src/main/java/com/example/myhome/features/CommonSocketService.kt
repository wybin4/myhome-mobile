package com.example.myhome.features

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.features.chat.dtos.MessageAddRequest
import com.example.myhome.features.chat.dtos.MessageAddResponse
import com.example.myhome.features.chat.dtos.MessageCreatedAtListItemResponse
import com.example.myhome.features.chat.dtos.MessageReadRequest
import com.example.myhome.features.event.dto.ServiceNotificationListItemResponse
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray

class CommonSocketService: BaseSocketService() {
    private val binder = LocalBinder()

    private val _newMessage = MutableLiveData<MessageAddResponse>()
    val newMessage: LiveData<MessageAddResponse> = _newMessage

    private val _hasUnreadNotifications = MutableLiveData(-1)
    val hasUnreadNotifications: LiveData<Int> = _hasUnreadNotifications

    private val _readMessages = MutableLiveData<List<MessageCreatedAtListItemResponse>>()
    val readMessages: LiveData<List<MessageCreatedAtListItemResponse>> = _readMessages

    private val _newNotification = MutableLiveData<ServiceNotificationListItemResponse>()
    val newNotification: LiveData<ServiceNotificationListItemResponse> = _newNotification

    private val _chatList = MutableLiveData<List<ChatListItemResponse>>()
    val chatList: LiveData<List<ChatListItemResponse>> = _chatList

    inner class LocalBinder : Binder() {
        fun getService(): CommonSocketService = this@CommonSocketService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    private val onChatList = Emitter.Listener { args ->
        val chats = args[0] as JSONArray
        val list = mutableListOf<ChatListItemResponse>()
        for (i in 0 until chats.length()) {
            val chatJson = chats.getJSONObject(i).toString()
            val chat = gson.fromJson(chatJson, ChatListItemResponse::class.java)
            list.add(chat)
        }
        _chatList.postValue(list)
    }

    fun sendSocketMessage(message: MessageAddRequest) {
        val jsonMessage = gson.toJson(message)
        socket?.emit("addMessage", jsonMessage)
    }

    fun readSocketMessage(message: MessageReadRequest) {
        val jsonMessage = gson.toJson(message)
        socket?.emit("readMessage", jsonMessage)
    }

    private val onNewMessage = Emitter.Listener { args ->
        val messageJson = args[0].toString()
        val message = gson.fromJson(messageJson, MessageAddResponse::class.java)
        _newMessage.postValue(message)
    }

    private val onReadMessages = Emitter.Listener { args ->
        val messages = args[0] as JSONArray
        val list = mutableListOf<MessageCreatedAtListItemResponse>()
        for (i in 0 until messages.length()) {
            val messageJson = messages.getJSONObject(i).toString()
            val message = gson.fromJson(messageJson, MessageCreatedAtListItemResponse::class.java)
            list.add(message)
        }
        _readMessages.postValue(list)
    }

    private val onNewChat = Emitter.Listener { args ->
        val chatJson = args[0].toString()
        val chat = gson.fromJson(chatJson, ChatListItemResponse::class.java)
        val currentList = _chatList.value.orEmpty().toMutableList()
        val newList = listOf(chat) + currentList
        _chatList.postValue(newList)
    }

    private val onUpdateChat = Emitter.Listener { args ->
        val chatJson = args[0].toString()
        val chat = gson.fromJson(chatJson, ChatListItemResponse::class.java)
        val currentList = _chatList.value.orEmpty().toMutableList()
        val index = currentList.indexOfFirst { it.id == chat.id }
        if (index != -1) {
            val existingChat = currentList[index]
            if (chat.countUnread == -1) {
                chat.countUnread = existingChat.countUnread
            }
            if (chat.receiverName == "") {
                chat.receiverName = existingChat.receiverName
            }
            currentList[index] = chat
        } else {
            if (chat.countUnread == -1) {
                chat.countUnread = 0
            }
            currentList.add(0, chat)
        }
        _chatList.postValue(currentList)
    }

    private val onHasUnreadNotifications = Emitter.Listener { args ->
        val hasUnread = args[0]?.toString()?.toInt() ?: -1
        _hasUnreadNotifications.postValue(hasUnread)
    }

    private val onNewNotification = Emitter.Listener { args ->
        val notificationJson = args[0].toString()
        val notification = gson.fromJson(notificationJson, ServiceNotificationListItemResponse::class.java)
        _newNotification.postValue(notification)
    }

    override fun startListeners() {
        socket?.apply {
            on("hasUnreadNotifications", onHasUnreadNotifications)
            on("chats", onChatList)
            on("newChat", onNewChat)
            on("updateChat", onUpdateChat)
            on("newMessage", onNewMessage)
            on("readMessages", onReadMessages)
            on("newNotification", onNewNotification)
            on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            connect()
        }
    }

    override fun stopListeners() {
        socket?.apply {
            off("hasUnreadNotifications", onHasUnreadNotifications)
            off("chats", onChatList)
            off("newChat", onNewChat)
            off("updateChat", onUpdateChat)
            off("newMessage", onNewMessage)
            off("readMessages", onReadMessages)
            off("newNotification", onNewNotification)
            off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        }
    }
}
