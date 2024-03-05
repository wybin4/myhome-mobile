package com.example.myhome.presentation.state.data.add

import android.app.Dialog
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.state.data.common.DataErrorStateUi
import com.example.myhome.presentation.state.data.common.DataLoadingStateUi
import com.example.myhome.presentation.state.data.common.DataSuccessStateUi

class DataAddStateManager(
    dataStateBinding: DataStateBinding,
    private val errorUi: DataErrorStateUi,
    private val successUi: DataSuccessStateUi,
    private val loadingUi: DataLoadingStateUi,
    dialog: Dialog,
    goBack: () -> Unit
) {
    private val dataAddBehavior = DataAddStateBehavior(dialog, goBack)
    private val uiManager = DataAddStateUI(dataStateBinding) {
        dataAddBehavior.setupBackAction()
    }

    fun observeState(resource: AddResource) {
        dataAddBehavior.setupVisibility(resource)
        when (resource) {
            is AddResource.Success -> successUi.showSuccessState()
            is AddResource.CodeError -> errorUi.showCodeErrorState()
            is AddResource.NetworkError -> errorUi.showNetworkErrorState(resource.message)
            is AddResource.Loading -> loadingUi.showLoadingState()
            else -> {}
        }
        setupDialogBehavior(resource)
    }

    private fun setupDialogBehavior(resource: AddResource) {
        when (resource) {
            is AddResource.NetworkError, is AddResource.CodeError, is AddResource.Success -> {
                uiManager.setupState()
                loadingUi.showNotLoadingState()
                dataAddBehavior.setupNotLoadingBehavior()
            }
            is AddResource.Loading -> dataAddBehavior.setupLoadingBehavior()
            else -> {}
        }
    }
}
