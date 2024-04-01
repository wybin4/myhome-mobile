package com.example.myhome.presentation.state

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.state.add.AddState
import com.example.myhome.presentation.state.add.AddStateManager
import com.example.myhome.presentation.state.common.LoadingStateUi
import com.example.myhome.presentation.state.get.GetState
import com.example.myhome.presentation.state.get.GetStateManager

class DataStateManager(
    context: FragmentActivity,
    dataStateBinding: DataStateBinding,
    successTitleText: String,
    successSubtitleText: String,
    loadingSubtitleText: String,
    goBack: () -> Unit
) : BaseDataStateManager(context, dataStateBinding, successTitleText, successSubtitleText) {
    private val loadingUi = LoadingStateUi(context, dataStateBinding, loadingSubtitleText)
    private val addStateManager = AddStateManager(dataStateBinding, errorUi, successUi, loadingUi, dialog, goBack)
    private val getStateManager = GetStateManager(dataStateBinding, errorUi, dialog, goBack)

    init {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dataStateBinding.root)
    }

    fun observeGetState(resource: GetState) {
        getStateManager.observeState(resource)
    }

    fun observeAddState(resource: AddState) {
        addStateManager.observeState(resource)
    }

}