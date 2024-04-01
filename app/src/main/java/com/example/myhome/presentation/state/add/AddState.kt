package com.example.myhome.presentation.state.add

sealed class AddState {
    data object Success : AddState()
    data object Loading : AddState()
    data object None : AddState()
    data object CodeError : AddState()
    data class NetworkError(val message: String) : AddState()
}