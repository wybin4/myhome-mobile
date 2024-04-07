package com.example.myhome.presentation.utils.managers.mainactivity

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myhome.databinding.CustomActionBarBinding

class ActionBarManager(
    private val activity: AppCompatActivity,
    private val onNotificationButton: () -> Unit
) {
    private lateinit var actionBarBinding: CustomActionBarBinding

    init {
        setupActionBar()
    }

    private fun setupActionBar() {
        actionBarBinding = CustomActionBarBinding.inflate(activity.layoutInflater)

        activity.supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(actionBarBinding.root, ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            ))
            setBackgroundDrawable(ColorDrawable(android.R.attr.windowBackground))
            elevation = 0f
        }
        actionBarBinding.notificationListButton.setOnClickListener {
            onNotificationButton()
        }
    }

    fun toggleVisibility(isVisible: Boolean) {
        if (isVisible) {
            activity.supportActionBar?.hide()
            actionBarBinding.root.visibility = View.GONE
        } else {
            activity.supportActionBar?.show()
            actionBarBinding.root.visibility = View.VISIBLE
        }
    }

    fun setTitle(title: CharSequence?) {
        actionBarBinding.title.text = title
    }

    fun setUnreadDotVisibility(isVisible: Int) {
        actionBarBinding.unreadDot.post {
            actionBarBinding.unreadDot.visibility = if (isVisible == 1) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setPlaceholderVisibility(visibility: Int) {
        actionBarBinding.placeholder.visibility = visibility
    }

    fun handleStartDestination(isItStartDestination: Boolean) {
        if (isItStartDestination) {
            setPlaceholderVisibility(View.INVISIBLE)
        } else {
            setPlaceholderVisibility(View.GONE)
        }
    }

    fun handleGetChatDestination(isItGetChatDestination: Boolean) {
        activity.run {
            if (isItGetChatDestination) {
                supportActionBar?.hide()
                actionBarBinding.root.visibility = View.GONE
            } else {
                supportActionBar?.show()
                actionBarBinding.root.visibility = View.VISIBLE
            }
        }
    }

    fun setNotificationButtonVisibility(isVisible: Boolean) {
        val visibility = if (isVisible) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
        actionBarBinding.notificationListButton.visibility = visibility
    }

}