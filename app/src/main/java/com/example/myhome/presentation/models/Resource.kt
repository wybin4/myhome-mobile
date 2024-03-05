package com.example.myhome.presentation.models

sealed class Resource {
    data object Success : Resource()
    data object Loading : Resource()
    data object Empty : Resource()
    data class Error(val message: String) : Resource()
}