package com.example.myhome.features

import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.myhome.features.auth.JwtTokenStore
import com.example.myhome.features.auth.services.RefreshApiService
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseSocketService: LifecycleService() {
    @Inject
    lateinit var tokenManager: JwtTokenStore

    @Inject
    lateinit var refreshTokenService: RefreshApiService

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

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    protected val onConnectError = Emitter.Listener { _ ->
        _socketError.postValue("Websocket connection error")
    }

    private suspend fun getAccessTokenOrRefreshToken(): String? {
        val accessToken = tokenManager.getAccessJwt()
        return if (accessToken != null) {
            accessToken
        } else {
            val newSessionResponse = refreshTokenService.refreshToken()
            if (newSessionResponse.isSuccessful && newSessionResponse.body() != null) {
                val newToken = newSessionResponse.body()!!.token
                tokenManager.saveAccessJwt(newToken)
                newToken
            } else {
                null
            }
        }
    }

    private fun initializeSocket() {
        try {
            lifecycleScope.launch {
                try {
                    val accessToken = getAccessTokenOrRefreshToken()
                    if (accessToken != null) {
                        val options = IO.Options().apply {
                            transports = arrayOf("websocket")
                            forceNew = true
                            extraHeaders = mutableMapOf<String, MutableList<String>>().apply {
                                put("Authorization", mutableListOf("Bearer $accessToken"))
                            }
                        }
                        socket = IO.socket(SOCKET_URL, options)
                    } else {
                        _socketError.postValue("Failed to initialize socket")
                    }
                } catch (e: Exception) {
                    _socketError.postValue("Failed to initialize socket")
                }
            }
        } catch (e: Exception) {
            Log.e("initializeSocket", e.toString())
            _socketError.postValue("Failed to initialize socket")
        }
    }

    private fun disconnectSocket() {
        socket?.disconnect()
    }
}
