package com.example.myhome.presentation.features.charge.state

data class ChargeGetState(
    val loadingVisibility: Int,
    val successVisibility: Int,
    val networkErrorVisibility: Int,
    val codeErrorVisibility: Int,
    val errorMessage: String?
)
