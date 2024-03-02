package com.example.myhome.utils.models

import android.app.Dialog

data class ListState(
    val loadingVisibility: Int,
    val successVisibility: Int,
    val emptyVisibility: Int,
    val errorVisibility: Int,
    val addButtonLayoutVisibility: Int?,
    val errorMessage: String?
)
