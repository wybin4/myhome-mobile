package com.example.myhome.presentation.features.charge.state

import android.view.View

sealed class ChargeCombinedState {
    data class Success(
        val isSecondCodeError: Boolean = false,
        val isSecondEmpty: Boolean = false
    ) : ChargeCombinedState()

    data object Loading : ChargeCombinedState()
    data object CodeError : ChargeCombinedState()
    data class NetworkError(val message: String) : ChargeCombinedState()

    val loadingVisibility: Int
        get() = if (this is Loading) View.VISIBLE else View.GONE

    val codeErrorVisibility: Int
        get() = if (this is CodeError) View.VISIBLE else View.GONE

    val networkErrorVisibility: Int
        get() = if (this is NetworkError) View.VISIBLE else View.GONE

    val successVisibility: Int
        get() = if (this is Success) View.VISIBLE else View.GONE

    val secondSuccessVisibility: Int
        get() = if (this is Success && !isSecondCodeError && !isSecondEmpty) View.VISIBLE else View.GONE

    val secondCodeErrorVisibility: Int
        get() = if (this is Success && isSecondCodeError) View.VISIBLE else View.GONE

    val secondEmptyVisibility: Int
        get() = if (this is Success && isSecondEmpty) View.VISIBLE else View.GONE

    val errorMessage: String?
        get() = if (this is NetworkError) message else null
}
