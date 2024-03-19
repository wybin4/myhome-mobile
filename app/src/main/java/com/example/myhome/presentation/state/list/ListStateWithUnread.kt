package com.example.myhome.presentation.state.list

import android.view.View

sealed class ListStateWithUnread  {
    data class Success(val countUnread: Int) : ListStateWithUnread()
    data object Loading : ListStateWithUnread()
    data object Empty : ListStateWithUnread()
    data class Error(val message: String) : ListStateWithUnread()

    val dotVisibility: Int
        get() = when(this) {
            is Success -> if (countUnread > 0) View.VISIBLE else View.GONE
            else -> View.GONE
        }

    val loadingVisibility: Int
        get() = if (this is Loading) View.VISIBLE else View.GONE

    val errorVisibility: Int
        get() = if (this is Error) View.VISIBLE else View.GONE

    val addButtonVisibility: Int
        get() = if (this is Empty || this is Success) View.VISIBLE else View.GONE

    val emptyVisibility: Int
        get() = if (this is Empty) View.VISIBLE else View.GONE

    val successVisibility: Int
        get() = if (this is Success) View.VISIBLE else View.GONE

    val errorMessage: String?
        get() = if (this is Error) message else null

}