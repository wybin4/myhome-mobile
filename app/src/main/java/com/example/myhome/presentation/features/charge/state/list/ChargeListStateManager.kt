package com.example.myhome.presentation.features.charge.state.list

import android.view.View
import com.example.myhome.presentation.features.charge.models.resources.ChargeListResource

class ChargeListStateManager(
    private val updateViewState: (state: ChargeListState) -> Unit
) {
    fun observeStates(resource: ChargeListResource) {
        when (resource) {
            is ChargeListResource.Loading -> showLoadingState()
            is ChargeListResource.Success -> showSuccessState()
            is ChargeListResource.Empty -> showEmptyState()
            is ChargeListResource.CodeError -> showCodeErrorState()
            is ChargeListResource.NetworkError -> showNetworkErrorState(resource.message)
        }
    }

    private fun showLoadingState() {
        updateViewState(
            ChargeListState(
                loadingVisibility = View.VISIBLE,
                successVisibility = View.GONE,
                emptyVisibility = View.GONE,
                networkErrorVisibility = View.GONE,
                codeErrorVisibility = View.GONE,
                errorMessage = null
            )
        )
    }

    private fun showSuccessState() {
        updateViewState(
            ChargeListState(
                loadingVisibility = View.GONE,
                successVisibility = View.VISIBLE,
                emptyVisibility = View.GONE,
                networkErrorVisibility = View.GONE,
                codeErrorVisibility = View.GONE,
                errorMessage = null
            )
        )
    }

    private fun showEmptyState() {
        updateViewState(
            ChargeListState(
                loadingVisibility = View.GONE,
                successVisibility = View.GONE,
                emptyVisibility = View.VISIBLE,
                networkErrorVisibility = View.GONE,
                codeErrorVisibility = View.GONE,
                errorMessage = null
            )
        )
    }

    private fun showNetworkErrorState(message: String) {
        updateViewState(
            ChargeListState(
                loadingVisibility = View.GONE,
                successVisibility = View.GONE,
                emptyVisibility = View.GONE,
                networkErrorVisibility = View.VISIBLE,
                codeErrorVisibility = View.GONE,
                errorMessage = message
            )
        )
    }

    private fun showCodeErrorState() {
        updateViewState(
            ChargeListState(
                loadingVisibility = View.GONE,
                successVisibility = View.GONE,
                emptyVisibility = View.GONE,
                networkErrorVisibility = View.GONE,
                codeErrorVisibility = View.VISIBLE,
                errorMessage = null
            )
        )
    }

    private fun updateViewState(state: ChargeListState) {
        updateViewState.invoke(state)
    }

}