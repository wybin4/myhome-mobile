package com.example.myhome.presentation.state.data.get

import android.app.Dialog
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.state.data.common.DataErrorStateUi

class DataGetStateManager(
    private val dataStateBinding: DataStateBinding,
    private val errorUi: DataErrorStateUi,
    dialog: Dialog,
    goBack: () -> Unit
) {
    private val dataGetBehavior = DataGetStateBehavior(dialog, goBack)

    fun observeState(resource: Resource) {
        when (resource) {
            is Resource.Error -> errorUi.showNetworkErrorState(resource.message)
            is Resource.Empty -> errorUi.showCodeErrorState()
            else -> {}
        }
        setup(resource)
    }

    private fun setup(resource: Resource) {
        when (resource) {
            is Resource.Empty, is Resource.Error -> {
                dataStateBinding.backButton.setOnClickListener {
                    dataGetBehavior.setupBackAction()
                }
                dataGetBehavior.setupBehavior()
            }
            else -> {}
        }
    }
}
