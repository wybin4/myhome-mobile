package com.example.myhome.presentation.utils

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.myhome.presentation.utils.filters.ListStateWithFilter
import com.example.myhome.presentation.models.Resource

fun CombinedLoadStates.handleLoadState(resourceCallback: (Resource) -> Unit, isItemCountNullable: Boolean) {
    when (refresh) {
        is LoadState.Error -> {
            val errorMessage = (refresh as LoadState.Error).error.message
            resourceCallback(Resource.Error(errorMessage ?: ""))
        }
        else -> {}
    }

    if (source.refresh is LoadState.NotLoading && append.endOfPaginationReached && isItemCountNullable) {
        resourceCallback(Resource.Empty)
    }
}


fun CombinedLoadStates.handleLoadStateWithFilter(
    resourceCallback: (ListStateWithFilter) -> Unit,
    isItemCountNullable: Boolean, isFilter: Boolean) {
    when (refresh) {
        is LoadState.Error -> {
            val errorMessage = (refresh as LoadState.Error).error.message
            resourceCallback(ListStateWithFilter.Error(errorMessage ?: ""))
        }
        else -> {}
    }

    if (source.refresh is LoadState.NotLoading && append.endOfPaginationReached && isItemCountNullable) {
        if (isFilter) {
            resourceCallback(ListStateWithFilter.EmptyFilter)
        } else {
            resourceCallback(ListStateWithFilter.Empty)
        }
    }
}
