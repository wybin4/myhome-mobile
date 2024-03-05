package com.example.myhome.presentation.state.list

data class ListState(
    val loadingVisibility: Int,
    val successVisibility: Int,
    val emptyVisibility: Int,
    val errorVisibility: Int,
    val addButtonLayoutVisibility: Int?,
    val errorMessage: String?
)
