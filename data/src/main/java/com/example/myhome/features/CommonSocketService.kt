package com.example.myhome.features

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.features.chat.dtos.MessageAddRequest
import com.example.myhome.features.chat.dtos.MessageListItemResponse
import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray

class CommonSocketService: BaseSocketService() {
    private val binder = LocalBinder()

    private val _notificationList = MutableLiveData<List<ServiceNotificationListItemResponse>>()
    val notificationList: LiveData<List<ServiceNotificationListItemResponse>> = _notificationList

    private val _newMessage = MutableLiveData<MessageListItemResponse>()
    val newMessage: LiveData<MessageListItemResponse> = _newMessage

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

    override fun onBind(intent: Intent?): IBinder? {
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

    private val onNewMessage = Emitter.Listener { args ->
        val messageJson = args[0].toString()
        val message = gson.fromJson(messageJson, MessageListItemResponse::class.java)
        _newMessage.postValue(message)
    }

    private val onNewChat = Emitter.Listener { args ->
        val chatJson = args[0].toString()
        val chat = gson.fromJson(chatJson, ChatListItemResponse::class.java)
        val currentList = _chatList.value.orEmpty().toMutableList()
        val newList = listOf(chat) + currentList
        _chatList.postValue(newList)
    }

    private val onNotificationList = Emitter.Listener { args ->
        val notifications = args[0] as JSONArray
        val list = mutableListOf<ServiceNotificationListItemResponse>()
        for (i in 0 until notifications.length()) {
            val notificationJson = notifications.getJSONObject(i).toString()
            val notification = gson.fromJson(notificationJson, ServiceNotificationListItemResponse::class.java)
            list.add(notification)
        }
        _notificationList.postValue(list.sortedByDescending { it.createdAt })
    }

    private val onNewNotification = Emitter.Listener { args ->
        val notificationJson = args[0].toString()
        val notification = gson.fromJson(notificationJson, ServiceNotificationListItemResponse::class.java)
        val currentList = _notificationList.value.orEmpty().toMutableList()
        val newList = listOf(notification) + currentList
        _notificationList.postValue(newList)
        _newNotification.postValue(notification)
    }

    private val onReadNotifications = Emitter.Listener { args ->
        val notificationsArray = args[0] as JSONArray
        val newList = mutableListOf<ServiceNotificationListItemResponse>()
        for (i in 0 until notificationsArray.length()) {
            val notificationJson = notificationsArray.getJSONObject(i).toString()
            val notification = gson.fromJson(notificationJson, ServiceNotificationListItemResponse::class.java)
            newList.add(notification)
        }
        val currentList = _notificationList.value.orEmpty().toMutableList()
        for (notification in newList) {
            val index = currentList.indexOfFirst { it.id == notification.id }
            if (index != -1) {
                currentList[index] = notification
            } else {
                currentList.add(notification)
            }
        }
        val sortedList = currentList.sortedByDescending { it.createdAt }
        _notificationList.postValue(sortedList)
    }

    override fun startListeners() {
        socket?.apply {
            on("notifications", onNotificationList)
            on("chats", onChatList)
            on("newChat", onNewChat)
            on("newMessage", onNewMessage)
            on("newNotification", onNewNotification)
            on("readNotifications", onReadNotifications)
            on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            connect()
        }
    }

    override fun stopListeners() {
        socket?.apply {
            off("notifications", onNotificationList)
            off("chats", onChatList)
            on("newChat", onNewChat)
            on("newMessage", onNewMessage)
            on("newNotification", onNewNotification)
            on("readNotifications", onReadNotifications)
            off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        }
    }
}
