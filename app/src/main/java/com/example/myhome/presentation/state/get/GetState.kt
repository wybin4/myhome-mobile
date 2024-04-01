package com.example.myhome.presentation.state.get

sealed class GetState {
    data object Success : GetState()
    data object Loading : GetState()
    data object Empty : GetState()
    data class Error(val message: String) : GetState()
}