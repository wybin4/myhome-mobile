package com.example.myhome.presentation.utils.managers.mainactivity

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection

class ServiceManager(private val context: Context) {

    fun startService(serviceClass: Class<out Service>) {
        val serviceIntent = Intent(context, serviceClass)
        context.startService(serviceIntent)
    }

    fun stopService(serviceClass: Class<out Service>) {
        val serviceIntent = Intent(context, serviceClass)
        context.stopService(serviceIntent)
    }

    fun bindService(serviceClass: Class<out Service>, connection: ServiceConnection) {
        val serviceIntent = Intent(context, serviceClass)
        context.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(connection: ServiceConnection) {
        context.unbindService(connection)
    }
}
