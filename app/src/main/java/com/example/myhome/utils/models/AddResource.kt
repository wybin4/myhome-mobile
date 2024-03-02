package com.example.myhome.utils.models

sealed class AddResource {
    data object Success : AddResource()
    data object Loading : AddResource()
    data object None : AddResource()
    data object CodeError : AddResource()
    data class NetworkError(val message: String) : AddResource()
}