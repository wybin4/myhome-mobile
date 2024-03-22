package com.example.myhome.features

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter

abstract class BaseSocketService: Service() {
    protected val gson = Gson()

    private val _socketError = MutableLiveData<String>()
    val socketError: LiveData<String> = _socketError

    companion object {
        private const val SOCKET_URL = "https://personally-poetic-cattle.ngrok-free.app"
    }

    protected var socket: Socket? = null

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

    protected abstract fun startListeners()

    protected abstract fun stopListeners()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    protected val onConnectError = Emitter.Listener { _ ->
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

    private fun disconnectSocket() {
        socket?.disconnect()
    }
}
