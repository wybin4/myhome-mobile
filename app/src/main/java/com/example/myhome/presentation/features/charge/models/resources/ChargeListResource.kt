package com.example.myhome.presentation.features.charge.models.resources

sealed class ChargeListResource {
    data object Success : ChargeListResource()
    data object Loading : ChargeListResource()
    data object Empty : ChargeListResource()
    data object CodeError : ChargeListResource()
    data class NetworkError(val message: String) : ChargeListResource()
}