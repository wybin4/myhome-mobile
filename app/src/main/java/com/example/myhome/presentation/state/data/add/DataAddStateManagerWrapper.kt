package com.example.myhome.presentation.state.data.add

import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.state.data.BaseDataStateManager
import com.example.myhome.presentation.state.data.common.DataLoadingStateUi

class DataAddStateManagerWrapper(
    context: FragmentActivity,
    dataStateBinding: DataStateBinding,
    successTitleText: String,
    successSubtitleText: String,
    loadingSubtitleText: String,
    goBack: () -> Unit
) : BaseDataStateManager(context, dataStateBinding, successTitleText, successSubtitleText) {
    private val loadingUi = DataLoadingStateUi(context, dataStateBinding, loadingSubtitleText)
    private val dataStateManager = DataAddStateManager(dataStateBinding, errorUi, successUi, loadingUi, dialog, goBack)

    fun observeState(resource: AddResource) {
        dataStateManager.observeState(resource)
    }

}