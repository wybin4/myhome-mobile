package com.example.myhome.presentation.utils.managers.mainactivity

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.AppBarConfiguration
import com.example.myhome.R
import com.example.myhome.databinding.ActivityMainBinding

class NavigationManager(
    activity: AppCompatActivity,
    private val navController: NavController,
    private val mainBinding: ActivityMainBinding
) {
    var bottomNavigationManager: BottomNavigationManager
    var actionBarManager: ActionBarManager
    private var appBarConfiguration: AppBarConfiguration = AppBarConfiguration(
        setOf(
            R.id.list_meter, R.id.list_event,
            R.id.list_appeal, R.id.list_charge,
            R.id.list_chat
        )
    )

    init {
        activity.run {
            bottomNavigationManager = BottomNavigationManager(
                this, mainBinding.navView, navController,
                appBarConfiguration
            )
            actionBarManager = ActionBarManager(this) {
                navController.navigate(R.id.list_service_notification)
            }
        }
    }

    fun setupNavigation(destination: NavDestination) {
        destination.run {
            actionBarManager.setTitle(label)
            actionBarManager.handleGetChatDestination(id == R.id.get_chat)
            bottomNavigationManager.handleListChatDestination(id == R.id.list_chat)
            handleStartDestination(this)
        }
    }

    private fun handleStartDestination(destination: NavDestination) {
        val isItStartDestination = destination.id in appBarConfiguration.topLevelDestinations
        actionBarManager.handleStartDestination(isItStartDestination)
        bottomNavigationManager.setNavViewVisibility(isItStartDestination)

        val isLoginDestination = destination.id == R.id.login
        actionBarManager.toggleVisibility(isLoginDestination)
    }

}
