package com.example.myhome.utils.models

sealed class Resource {
    data object Success : Resource()
    data object Loading : Resource()
    data object Empty : Resource()
    data object Error : Resource()
}