package com.example.myhome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.myhome.databinding.ActivityMainBinding
import com.example.myhome.features.CommonSocketService
import com.example.myhome.presentation.state.list.ListStateWithUnread
import com.example.myhome.presentation.utils.managers.mainactivity.NavigationManager
import com.example.myhome.presentation.utils.managers.mainactivity.ServiceManager
import com.example.myhome.presentation.utils.pickers.permissions.NotificationPermissionPicker
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.TestOnly

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var navigationManager: NavigationManager
    private lateinit var serviceManager: ServiceManager

    private var isNotificationDestination = false
    private val notificationPermissionPicker = NotificationPermissionPicker(this)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navigationManager = NavigationManager(this, navController, mainBinding)
        setupNavigation()

        serviceManager = ServiceManager(this)

        observeResourceState()

        notificationPermissionPicker.checkStoragePermission()

        viewModel.unreadMessagesCount.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                navigationManager.bottomNavigationManager.setChatCircleVisibility(viewModel.unreadMessagesCount.get())
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

    private fun setupNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navigationManager.setupNavigation(destination)

            isNotificationDestination = destination.id == R.id.list_service_notification
            navigationManager.actionBarManager.setNotificationButtonVisibility(
                viewModel.notificationListState.value!! is ListStateWithUnread.Error || isNotificationDestination
            )
        }
    }

    private fun observeResourceState() {
        viewModel.notificationListState.observe(this) { resource ->
            navigationManager.actionBarManager.setNotificationButtonVisibility(
                resource is ListStateWithUnread.Error || isNotificationDestination
            )
            navigationManager.actionBarManager.setUnreadDotVisibility(resource.dotVisibility)
        }
    }

    @TestOnly
    fun getCurrentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        return navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceManager.stopService(NotificationService::class.java)
        serviceManager.unbindService(viewModel.getServiceConnection())
    }

    private fun startServices() {
        serviceManager.startService(CommonSocketService::class.java)
        serviceManager.bindService(CommonSocketService::class.java, viewModel.getServiceConnection())
        serviceManager.startService(NotificationService::class.java)
    }

}