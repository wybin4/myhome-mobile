package com.example.myhome.presentation.utils.managers.mainactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myhome.R
import com.example.myhome.databinding.BottomNavChatItemBinding
import com.example.myhome.presentation.features.chat.MessageFormatter
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationManager(
    private val activity: AppCompatActivity,
    private val navView: BottomNavigationView,
    private val navController: NavController,
    private val appBarConfiguration: AppBarConfiguration
) : MessageFormatter {
    private lateinit var chatCircleBinding: BottomNavChatItemBinding

    init {
        setupNavigation()
        setupActionBar()
    }

    private fun setupNavigation() {
        navView.setupWithNavController(navController)
        val navMenuView = navView.getChildAt(0) as BottomNavigationMenuView
        val itemView = navMenuView.getChildAt(2) as BottomNavigationItemView
        chatCircleBinding = BottomNavChatItemBinding.inflate(
            LayoutInflater.from(activity),
            navMenuView,
            false
        )
        itemView.addView(chatCircleBinding.root)
    }

    private fun setupActionBar() {
        activity.setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun setNavViewVisibility(isVisible: Boolean) {
        if (isVisible) {
            navView.visibility = View.VISIBLE
        } else {
            navView.visibility = View.GONE
        }
    }

    fun handleListChatDestination(isItListChatDestination: Boolean) {
        if (isItListChatDestination) {
            chatCircleBinding.messageCount.setMarginTop(R.dimen.chat_non_top_margin)
        } else {
            chatCircleBinding.messageCount.setMarginTop(R.dimen.chat_top_margin)
        }
    }

    fun setChatCircleVisibility(count: Int) {
        if (count > 0) {
            chatCircleBinding.messageCount.visibility = View.VISIBLE
            chatCircleBinding.messageCount.text = formatCountUnread(count)
        } else {
            chatCircleBinding.messageCount.visibility = View.GONE
        }
    }

    private fun TextView.setMarginTop(@DimenRes marginTopResId: Int) {
        val marginTop = resources.getDimensionPixelSize(marginTopResId)
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = marginTop
        layoutParams = params
    }
}