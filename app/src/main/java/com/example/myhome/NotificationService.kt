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
import androidx.lifecycle.MutableLiveData
import com.example.myhome.features.CommonSocketService
import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiType
import com.example.myhome.presentation.utils.pickers.IconPicker

class NotificationService : Service(), IconPicker {
    private val localBinder: MutableLiveData<CommonSocketService.LocalBinder?> = MutableLiveData<CommonSocketService.LocalBinder?>()

    private val channelId = "CHANNEL_ID_NOTIFICATION"
    private val channelDescription = "MyHome notifications"

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            val binder: CommonSocketService.LocalBinder = iBinder as CommonSocketService.LocalBinder
            localBinder.postValue(binder)
            binder.getService().let { service ->
                service.newNotification.observeForever { notification ->
                    if (notification != null) {
                        onNewNotification(notification)
                    }
                }
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            localBinder.postValue(null)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val intent = Intent(applicationContext, CommonSocketService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    fun onNewNotification(notification: ServiceNotificationListItemResponse) {
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
            val existingChannel = notificationManager.getNotificationChannel(channelId)
            if (existingChannel == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                val newChannel = NotificationChannel(channelId, channelDescription, importance)
                newChannel.lightColor = Color.GREEN
                newChannel.enableVibration(true)
                notificationManager.createNotificationChannel(newChannel)
            }
        }

        notificationManager.notify(notification.id, notificationBuilder.build())
    }
}
