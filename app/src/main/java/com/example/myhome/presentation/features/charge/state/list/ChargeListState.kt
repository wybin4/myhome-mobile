package com.example.myhome.presentation.features.charge.state.list

data class ChargeListState(
    val loadingVisibility: Int,
    val successVisibility: Int,
    val emptyVisibility: Int,
    val networkErrorVisibility: Int,
    val codeErrorVisibility: Int,
    val errorMessage: String?
)
