package com.example.myhome.presentation.state.list

import android.view.View
import com.example.myhome.presentation.models.Resource

class ListStateManager(
    private val updateViewState: (state: ListState) -> Unit
) {
    fun observeStates(resource: Resource) {
        when (resource) {
            is Resource.Loading -> showLoadingState()
            is Resource.Success -> showSuccessState()
            is Resource.Empty -> showEmptyState()
            is Resource.Error -> showErrorState(resource.message)
        }
    }

    private fun showLoadingState() {
        updateViewState(
            ListState(
                loadingVisibility = View.VISIBLE,
                successVisibility = View.GONE,
                emptyVisibility = View.GONE,
                errorVisibility = View.GONE,
                addButtonLayoutVisibility = View.GONE,
                errorMessage = null
            )
        )
    }

    private fun showSuccessState() {
        updateViewState(
            ListState(
                loadingVisibility = View.GONE,
                successVisibility = View.VISIBLE,
                emptyVisibility = View.GONE,
                errorVisibility = View.GONE,
                addButtonLayoutVisibility = View.VISIBLE,
                errorMessage = null
            )
        )
    }

    private fun showEmptyState() {
        updateViewState(
            ListState(
                loadingVisibility = View.GONE,
                successVisibility = View.GONE,
                emptyVisibility = View.VISIBLE,
                errorVisibility = View.GONE,
                addButtonLayoutVisibility = View.VISIBLE,
                errorMessage = null
            )
        )
    }

    private fun showErrorState(message: String) {
        updateViewState(
            ListState(
                loadingVisibility = View.GONE,
                successVisibility = View.GONE,
                emptyVisibility = View.GONE,
                errorVisibility = View.VISIBLE,
                addButtonLayoutVisibility = View.GONE,
                errorMessage = message
            )
        )
    }

    private fun updateViewState(state: ListState) {
        updateViewState.invoke(state)
    }

}