package com.example.myhome.presentation.features.chat

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.state.data.add.DataAddStateBehavior
import com.example.myhome.presentation.state.data.add.DataAddStateUI
import com.example.myhome.presentation.state.data.common.DataErrorStateUi
import com.example.myhome.presentation.state.data.common.DataLoadingStateUi

class ChatDataAddStateManager(
    context: FragmentActivity,
    dataStateBinding: DataStateBinding,
    loadingSubtitleText: String,
    goBack: () -> Unit
) {
    private val dialog = Dialog(context)
    private val errorUi = DataErrorStateUi(context, dataStateBinding)
    private val loadingUi = DataLoadingStateUi(context, dataStateBinding, loadingSubtitleText)

    private val dataAddBehavior = DataAddStateBehavior(dialog, goBack)
    private val uiManager = DataAddStateUI(dataStateBinding) {
        dataAddBehavior.setupBackAction()
    }

    init {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dataStateBinding.root)
    }

    fun observeState(resource: AddResource) {
        dataAddBehavior.setupVisibility(resource)
        when (resource) {
            is AddResource.CodeError -> errorUi.showCodeErrorState()
            is AddResource.NetworkError -> errorUi.showNetworkErrorState(resource.message)
            is AddResource.Loading -> loadingUi.showLoadingState()
            else -> {}
        }
        setupDialogBehavior(resource)
    }

    private fun setupDialogBehavior(resource: AddResource) {
        when (resource) {
            is AddResource.NetworkError, is AddResource.CodeError, AddResource.Loading -> {
                uiManager.setupState()
                dataAddBehavior.setupNotLoadingBehavior()
            }
            is AddResource.Success -> {
                dataAddBehavior.setupBackAction()
            }
            else -> {}
        }
    }
}
