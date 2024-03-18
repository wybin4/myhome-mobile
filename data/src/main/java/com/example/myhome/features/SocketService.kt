package com.example.myhome.features

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse
import com.example.myhome.models.NotificationListener
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONException

class SocketService: Service() {
    private val binder = LocalBinder()
    private val gson = Gson()
    private val listeners = mutableListOf<NotificationListener>()

    private val _notificationList = MutableLiveData<List<ServiceNotificationListItemResponse>>()
    val notificationList: LiveData<List<ServiceNotificationListItemResponse>> = _notificationList

    private val _socketError = MutableLiveData<String>()
    val socketError: LiveData<String> = _socketError

    companion object {
        private const val SOCKET_URL = "https://personally-poetic-cattle.ngrok-free.app"
    }

    private var socket: Socket? = null

    inner class LocalBinder : Binder() {
        fun getService(): SocketService = this@SocketService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        initializeSocket()
        startListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnectSocket()
        stopListeners()
    }

    fun addListener(listener: NotificationListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: NotificationListener) {
        listeners.remove(listener)
    }


    private val onNotificationList = Emitter.Listener { args ->
        try {
            val notifications = args[0] as JSONArray
            val list = mutableListOf<ServiceNotificationListItemResponse>()
            for (i in 0 until notifications.length()) {
                val notificationJson = notifications.getJSONObject(i).toString()
                val notification = gson.fromJson(notificationJson, ServiceNotificationListItemResponse::class.java)
                list.add(notification)
            }
            _notificationList.postValue(list.sortedByDescending { it.createdAt })
        } catch (e: JSONException) {
            _socketError.postValue("Failed to get notifications")
        }
    }

    private val onNewNotification = Emitter.Listener { args ->
        try {
            val notificationJson = args[0].toString()
            val notification = gson.fromJson(notificationJson, ServiceNotificationListItemResponse::class.java)
            val currentList = _notificationList.value.orEmpty().toMutableList()
            val newList = listOf(notification) + currentList
            _notificationList.postValue(newList)
            Log.e("SocketService", listeners.toString())
            for (listener in listeners) {
                listener.onNewNotification(notification)
            }
        } catch (e: JSONException) {
            _socketError.postValue("Failed to get notification")
        }
    }

    private val onReadNotifications = Emitter.Listener { args ->
        try {
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
        } catch (e: JSONException) {
            _socketError.postValue("Failed to get notifications")
        }
    }

    private val onConnectError = Emitter.Listener { _ ->
        _socketError.postValue("Websocket connection error")
    }

    private fun initializeSocket() {
        try {
            val options = IO.Options().apply {
                transports = arrayOf("websocket")
                forceNew = true
            }
            socket = IO.socket(SOCKET_URL, options)
        } catch (e: Exception) {
            _socketError.postValue("Failed to initialize socket")
        }
    }

    private fun startListeners() {
        socket?.apply {
            on("notifications", onNotificationList)
            on("newNotification", onNewNotification)
            on("readNotifications", onReadNotifications)
            on(Socket.EVENT_CONNECT_ERROR, onConnectError)
            connect()
        }
    }

    private fun stopListeners() {
        socket?.apply {
            off("notifications", onNotificationList)
            on("newNotification", onNewNotification)
            on("readNotifications", onReadNotifications)
            off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        }
    }

    private fun disconnectSocket() {
        socket?.disconnect()
    }
}
