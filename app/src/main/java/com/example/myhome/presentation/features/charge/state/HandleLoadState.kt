package com.example.myhome.presentation.features.charge.state

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.myhome.presentation.features.charge.models.resources.ChargeListResource

fun CombinedLoadStates.chargeHandleLoadState(resourceCallback: (ChargeListResource) -> Unit, isItemCountNullable: Boolean) {
    when (refresh) {
        is LoadState.Error -> {
            val errorMessage = (refresh as LoadState.Error).error.message
            resourceCallback(ChargeListResource.NetworkError(errorMessage ?: ""))
        }
        else -> {}
    }

    if (source.refresh is LoadState.NotLoading && append.endOfPaginationReached && isItemCountNullable) {
        resourceCallback(ChargeListResource.Empty)
    }

    if (source.refresh is LoadState.NotLoading && !append.endOfPaginationReached && !isItemCountNullable) {
        resourceCallback(ChargeListResource.Success)
    }
}
