package com.example.myhome.presentation.state.list

import android.view.View

sealed class ListState  {
    data object Success : ListState()
    data object Loading : ListState()
    data object Empty : ListState()
    data class Error(val message: String) : ListState()

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