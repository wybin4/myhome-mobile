package com.example.myhome.features

import android.util.Log
import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONArray
import com.google.gson.Gson

class SocketService {
    private var socket: Socket
    private val gson = Gson()

    init {
        val opts = IO.Options()
        opts.transports = arrayOf("websocket")
        opts.forceNew = true
        socket = IO.socket("https://wealthy-barnacle-secure.ngrok-free.app", opts)
    }

    fun connect() {
        socket.connect()
    }

    fun disconnect() {
        socket.disconnect()
    }

    fun setNotificationsListener(listener: (List<ServiceNotificationListItemResponse>) -> Unit) {
        socket.on("notifications") { args ->
            val notifications = args[0] as JSONArray
            val list = mutableListOf<ServiceNotificationListItemResponse>()
            for (i in 0 until notifications.length()) {
                val notificationJson = notifications.getJSONObject(i).toString()
                val notification = gson.fromJson(notificationJson, ServiceNotificationListItemResponse::class.java)
                list.add(notification)
            }
            listener(list)
        }
    }
}
