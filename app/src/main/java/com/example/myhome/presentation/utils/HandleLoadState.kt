package com.example.myhome.presentation.utils

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.myhome.presentation.state.list.ListState
import com.example.myhome.presentation.utils.filters.ListStateWithFilter

fun CombinedLoadStates.handleLoadState(resourceCallback: (ListState) -> Unit, isItemCountNullable: Boolean) {
    when (refresh) {
        is LoadState.Error -> {
            val errorMessage = (refresh as LoadState.Error).error.message
            resourceCallback(ListState.Error(errorMessage ?: ""))
        }
        else -> {}
    }

    if (source.refresh is LoadState.NotLoading && append.endOfPaginationReached && isItemCountNullable) {
        resourceCallback(ListState.Empty)
    }

    if (source.refresh is LoadState.NotLoading && !append.endOfPaginationReached && !isItemCountNullable) {
        resourceCallback(ListState.Success)
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

    if (source.refresh is LoadState.NotLoading && !append.endOfPaginationReached && !isItemCountNullable) {
        resourceCallback(ListStateWithFilter.Success)
    }
}
