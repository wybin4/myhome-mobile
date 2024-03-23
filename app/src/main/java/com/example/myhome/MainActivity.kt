package com.example.myhome

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.DimenRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myhome.databinding.ActivityMainBinding
import com.example.myhome.databinding.BottomNavChatItemBinding
import com.example.myhome.databinding.CustomActionBarBinding
import com.example.myhome.features.CommonSocketService
import com.example.myhome.presentation.state.list.ListStateWithUnread
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var actionBarBinding: CustomActionBarBinding
    private lateinit var chatCircleBinding: BottomNavChatItemBinding
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }

        viewModel.unreadMessagesCount.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val unreadMessagesCount = viewModel.unreadMessagesCount.get()
                if (unreadMessagesCount > 0) {
                    chatCircleBinding.messageCount.visibility = View.VISIBLE
                    chatCircleBinding.messageCount.text = viewModel.formatCountUnread(unreadMessagesCount)
                } else {
                    chatCircleBinding.messageCount.visibility = View.GONE
                }
            }
        })

        startServices()

        checkIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            checkIntent(intent)
        }
    }

    private fun checkIntent(intent: Intent) {
        val fragmentToOpen = intent.getStringExtra("fragment_to_open")
        if (fragmentToOpen == "ServiceNotificationListView") {
            navController.navigate(R.id.list_service_notification)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
         appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.list_meter, R.id.list_event,
                R.id.list_appeal, R.id.list_charge,
                R.id.list_chat
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
        val navMenuView = navView.getChildAt(0) as BottomNavigationMenuView
        val itemView = navMenuView.getChildAt(2) as BottomNavigationItemView
        chatCircleBinding = BottomNavChatItemBinding.inflate(
            LayoutInflater.from(this),
            navMenuView,
            false
        )
        itemView.addView(chatCircleBinding.root)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isStartDestination = destination.id in appBarConfiguration.topLevelDestinations
            if (isStartDestination) {
                actionBarBinding.placeholder.visibility = View.INVISIBLE
                navView.visibility = View.VISIBLE
            } else {
                actionBarBinding.placeholder.visibility = View.GONE
                navView.visibility = View.GONE
            }
            if (destination.id == R.id.get_chat) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
            if (destination.id == R.id.list_chat) {
                chatCircleBinding.messageCount.setMarginTop(R.dimen.chat_non_top_margin)
            } else {
                chatCircleBinding.messageCount.setMarginTop(R.dimen.chat_top_margin)
            }
            isNotificationDestination = destination.id == R.id.list_service_notification
            updateNotificationListButton(viewModel.notificationListState.value!!)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            actionBarBinding.title.text = destination.label
        }
    }

    private fun TextView.setMarginTop(@DimenRes marginTopResId: Int) {
        val marginTop = resources.getDimensionPixelSize(marginTopResId)
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = marginTop
        layoutParams = params
    }

    private fun observeResourceState() {
        viewModel.notificationListState.observe(this) { resource ->
            actionBarBinding.apply {
                updateNotificationListButton(resource)
                unreadDot.visibility = resource.dotVisibility
            }
        }
    }

    private fun updateNotificationListButton(resource: ListStateWithUnread) {
        actionBarBinding.apply {
            if (resource is ListStateWithUnread.Error || isNotificationDestination) {
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

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, NotificationService::class.java))
        unbindService(viewModel.getServiceConnection())
    }

    private fun startServices() {
        val commonSocketServiceIntent = Intent(this, CommonSocketService::class.java)
        startService(commonSocketServiceIntent)
        bindService(commonSocketServiceIntent, viewModel.getServiceConnection(), BIND_AUTO_CREATE)

        startService(Intent(this, NotificationService::class.java))
    }

}