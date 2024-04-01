package com.example.myhome.presentation.state.get

import android.app.Dialog
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.state.common.ErrorStateUi

class GetStateManager(
    private val dataStateBinding: DataStateBinding,
    private val errorUi: ErrorStateUi,
    dialog: Dialog,
    goBack: () -> Unit
) {
    private val dataGetBehavior = GetStateBehavior(dialog, goBack)

    fun observeState(resource: GetState) {
        when (resource) {
            is GetState.Error -> errorUi.showNetworkErrorState(resource.message)
            is GetState.Empty -> errorUi.showCodeErrorState()
            else -> {}
        }
        setup(resource)
    }

    private fun setup(resource: GetState) {
        when (resource) {
            is GetState.Empty, is GetState.Error -> {
                dataStateBinding.backButton.setOnClickListener {
                    dataGetBehavior.setupBackAction()
                }
                dataGetBehavior.setupBehavior()
            }
            else -> {}
        }
    }
}
