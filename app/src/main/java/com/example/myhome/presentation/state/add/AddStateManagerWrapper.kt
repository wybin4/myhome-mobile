package com.example.myhome.presentation.state.add

import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.state.BaseDataStateManager
import com.example.myhome.presentation.state.common.LoadingStateUi

class AddStateManagerWrapper(
    context: FragmentActivity,
    dataStateBinding: DataStateBinding,
    successTitleText: String,
    successSubtitleText: String,
    loadingSubtitleText: String,
    goBack: () -> Unit
) : BaseDataStateManager(context, dataStateBinding, successTitleText, successSubtitleText) {
    private val loadingUi = LoadingStateUi(context, dataStateBinding, loadingSubtitleText)
    private val dataStateManager = AddStateManager(dataStateBinding, errorUi, successUi, loadingUi, dialog, goBack)

    fun observeState(resource: AddState) {
        dataStateManager.observeState(resource)
    }

}