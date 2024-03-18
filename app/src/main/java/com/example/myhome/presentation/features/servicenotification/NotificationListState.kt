package com.example.myhome.presentation.features.servicenotification

import android.view.View

sealed class NotificationListState {
    data class Success(val hasUnread: Boolean) : NotificationListState()
    data object Loading : NotificationListState()
    data object Empty : NotificationListState()
    data class Error(val message: String) : NotificationListState()

    val dotVisibility: Int
        get() = when(this) {
            is Success -> if (hasUnread) View.VISIBLE else View.GONE
            else -> View.GONE
        }

    val loadingVisibility: Int
        get() = if (this is Loading) View.VISIBLE else View.GONE

    val errorVisibility: Int
        get() = if (this is Error) View.VISIBLE else View.GONE

    val emptyVisibility: Int
        get() = if (this is Empty) View.VISIBLE else View.GONE

    val successVisibility: Int
        get() = if (this is Success) View.VISIBLE else View.GONE

    val errorMessage: String?
        get() = if (this is Error) message else null

}