package com.example.myhome.presentation.features.chat

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.state.add.AddState
import com.example.myhome.presentation.state.add.AddStateBehavior
import com.example.myhome.presentation.state.add.DataAddStateUI
import com.example.myhome.presentation.state.common.ErrorStateUi
import com.example.myhome.presentation.state.common.LoadingStateUi

class ChatAddStateManager(
    context: FragmentActivity,
    dataStateBinding: DataStateBinding,
    loadingSubtitleText: String,
    goBack: () -> Unit
) {
    private val dialog = Dialog(context)
    private val errorUi = ErrorStateUi(context, dataStateBinding)
    private val loadingUi = LoadingStateUi(context, dataStateBinding, loadingSubtitleText)

    private val dataAddBehavior = AddStateBehavior(dialog, goBack)
    private val uiManager = DataAddStateUI(dataStateBinding) {
        dataAddBehavior.setupBackAction()
    }

    init {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dataStateBinding.root)
    }

    fun observeState(resource: AddState) {
        dataAddBehavior.setupVisibility(resource)
        when (resource) {
            is AddState.CodeError -> errorUi.showCodeErrorState()
            is AddState.NetworkError -> errorUi.showNetworkErrorState(resource.message)
            is AddState.Loading -> loadingUi.showLoadingState()
            else -> {}
        }
        setupDialogBehavior(resource)
    }

    private fun setupDialogBehavior(resource: AddState) {
        when (resource) {
            is AddState.NetworkError, is AddState.CodeError, AddState.Loading -> {
                uiManager.setupState()
                dataAddBehavior.setupNotLoadingBehavior()
            }
            is AddState.Success -> {
                dataAddBehavior.setupBackAction()
            }
            else -> {}
        }
    }
}
