package com.example.myhome.presentation.utils.filters

import android.view.View

sealed class ListStateWithFilter  {
    data object Success : ListStateWithFilter()
    data object Loading : ListStateWithFilter()
    data object Empty : ListStateWithFilter()
    data object EmptyFilter : ListStateWithFilter()
    data class Error(val message: String) : ListStateWithFilter()

    val loadingVisibility: Int
        get() = if (this is Loading) View.VISIBLE else View.GONE

    val errorVisibility: Int
        get() = if (this is Error) View.VISIBLE else View.GONE

    val addButtonVisibility: Int
        get() = if (this is Empty || this is Success) View.VISIBLE else View.GONE

    val emptyVisibility: Int
        get() = if (this is Empty) View.VISIBLE else View.GONE

    val emptyFilterVisibility: Int
        get() = if (this is EmptyFilter) View.VISIBLE else View.GONE

    val successVisibility: Int
        get() = if (this is Success) View.VISIBLE else View.GONE

    val errorMessage: String?
        get() = if (this is Error) message else null

}