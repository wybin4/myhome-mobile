package com.example.myhome

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myhome.features.SocketService
import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse
import com.example.myhome.models.NotificationListener
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiType
import com.example.myhome.presentation.utils.pickers.IconPicker

class NotificationService: Service(), NotificationListener, IconPicker {
    lateinit var socketService: SocketService

    private val channelId = "CHANNEL_ID_NOTIFICATION"
    private val channelDescription = "MyHome notifications"

    private var isSocketServiceBound: Boolean = false

    private val socketServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as SocketService.LocalBinder
            socketService = binder.getService()
            isSocketServiceBound = true
            socketService.addListener(this@NotificationService)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isSocketServiceBound = false
            socketService.removeListener(this@NotificationService)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val socketServiceIntent = Intent(applicationContext, SocketService::class.java)
        bindService(socketServiceIntent, socketServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isSocketServiceBound) {
            unbindService(socketServiceConnection)
            isSocketServiceBound = false
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onNewNotification(notification: ServiceNotificationListItemResponse) {
        val context = applicationContext

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(
                getServiceNotificationIcon(
                    ServiceNotificationUiType.fromServiceNotificationType(notification.type)
                )
            )
            .setColor(ContextCompat.getColor(this, R.color.primary))
            .setContentTitle(notification.title)
            .setContentText(notification.text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("fragment_to_open", "ServiceNotificationListView")
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = notificationManager.getNotificationChannel(channelId)
            if (notificationChannel == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                val notificationChannel = NotificationChannel(channelId, channelDescription, importance)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        notificationManager.notify(notification.id, notificationBuilder.build())
    }
}