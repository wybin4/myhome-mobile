package com.example.myhome

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myhome.databinding.ActivityMainBinding
import com.example.myhome.databinding.CustomActionBarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var actionBarBinding: CustomActionBarBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        actionBarBinding = CustomActionBarBinding.inflate(layoutInflater)
        setupActionBar()

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        setupNavigation(mainBinding.navView)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
         appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.list_meter, R.id.navigation_events,
                R.id.list_appeal
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
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            actionBarBinding.title.text = destination.label
        }
    }

}