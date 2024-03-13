package com.example.myhome.presentation.features.charge.models.resources

sealed class ChargeGetResource {
    data object Success : ChargeGetResource()
    data object Loading : ChargeGetResource()
    data object CodeError : ChargeGetResource()
    data class NetworkError(val message: String) : ChargeGetResource()
}