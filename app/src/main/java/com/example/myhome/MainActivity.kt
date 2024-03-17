package com.example.myhome

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myhome.databinding.ActivityMainBinding
import com.example.myhome.databinding.CustomActionBarBinding
import com.example.myhome.features.SocketService
import com.example.myhome.presentation.features.servicenotification.list.ServiceNotificationListView
import com.example.myhome.presentation.state.list.NotificationListState
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var actionBarBinding: CustomActionBarBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var isNotificationDestination = false

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        actionBarBinding = CustomActionBarBinding.inflate(layoutInflater)
        actionBarBinding.notificationListButton.setOnClickListener {
            navController.navigate(R.id.list_service_notification)
        }
        setupActionBar()
        observeResourceState()

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        setupNavigation(mainBinding.navView)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
         appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.list_meter, R.id.list_event,
                R.id.list_appeal, R.id.list_charge
            )
        )

        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(actionBarBinding.root, ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            ))
            setBackgroundDrawable(ColorDrawable(android.R.attr.windowBackground))
            elevation = 0f
        }
    }

    private fun setupNavigation(navView: BottomNavigationView) {
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isStartDestination = destination.id in appBarConfiguration.topLevelDestinations
            if (isStartDestination) {
                actionBarBinding.placeholder.visibility = View.INVISIBLE
                navView.visibility = View.VISIBLE
            } else {
                actionBarBinding.placeholder.visibility = View.GONE
                navView.visibility = View.GONE
            }
            isNotificationDestination = destination.id == R.id.list_service_notification
            updateNotificationListButton(viewModel.listState.value!!)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            actionBarBinding.title.text = destination.label
        }
    }

    private fun observeResourceState() {
        viewModel.listState.observe(this) { resource ->
            actionBarBinding.apply {
                updateNotificationListButton(resource)
                unreadDot.visibility = resource.dotVisibility
            }
        }
        viewModel.newNotification.observe(this) {
            if (it != null) {
                sendNotification(this)
            }
        }
    }

    private fun updateNotificationListButton(resource: NotificationListState) {
        actionBarBinding.apply {
            if (resource is NotificationListState.Error || isNotificationDestination) {
                notificationListButton.visibility = View.INVISIBLE
            } else {
                notificationListButton.visibility = View.VISIBLE
            }
        }
    }

    fun getCurrentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        return navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
    }

    private fun sendNotification(context: Context) {
        val channelId = "CHANNEL_ID_NOTIFICATION"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notifications_logo)
            .setContentTitle("Notification Title")
            .setContentText("Some text for notification here")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(context, ServiceNotificationListView::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onResume() {
        super.onResume()
        startService()
    }

    override fun onStop() {
        super.onStop()
        if (viewModel.getBinder() != null) {
            unbindService(viewModel.getServiceConnection())
        }
    }

    private fun startService() {
        val serviceIntent = Intent(this, SocketService::class.java)
        startService(serviceIntent)
        bindService()
    }

    private fun bindService() {
        val serviceBindIntent = Intent(this, SocketService::class.java)
        bindService(serviceBindIntent, viewModel.getServiceConnection(), BIND_AUTO_CREATE)
    }

}