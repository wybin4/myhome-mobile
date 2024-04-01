package com.example.myhome.presentation.state.add

import android.app.Dialog
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.state.common.ErrorStateUi
import com.example.myhome.presentation.state.common.LoadingStateUi
import com.example.myhome.presentation.state.common.SuccessStateUi

class AddStateManager(
    dataStateBinding: DataStateBinding,
    private val errorUi: ErrorStateUi,
    private val successUi: SuccessStateUi,
    private val loadingUi: LoadingStateUi,
    dialog: Dialog,
    goBack: () -> Unit
) {
    private val dataAddBehavior = AddStateBehavior(dialog, goBack)
    private val uiManager = DataAddStateUI(dataStateBinding) {
        dataAddBehavior.setupBackAction()
    }

    fun observeState(resource: AddState) {
        dataAddBehavior.setupVisibility(resource)
        when (resource) {
            is AddState.Success -> successUi.showSuccessState()
            is AddState.CodeError -> errorUi.showCodeErrorState()
            is AddState.NetworkError -> errorUi.showNetworkErrorState(resource.message)
            is AddState.Loading -> loadingUi.showLoadingState()
            else -> {}
        }
        setupDialogBehavior(resource)
    }

    private fun setupDialogBehavior(resource: AddState) {
        when (resource) {
            is AddState.NetworkError, is AddState.CodeError, is AddState.Success -> {
                uiManager.setupState()
                loadingUi.showNotLoadingState()
                dataAddBehavior.setupNotLoadingBehavior()
            }
            is AddState.Loading -> dataAddBehavior.setupLoadingBehavior()
            else -> {}
        }
    }
}
