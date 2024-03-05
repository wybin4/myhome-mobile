package com.example.myhome.presentation.state.data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.state.data.add.DataAddStateManager
import com.example.myhome.presentation.state.data.common.DataLoadingStateUi
import com.example.myhome.presentation.state.data.get.DataGetStateManager

class DataStateManager(
    context: FragmentActivity,
    dataStateBinding: DataStateBinding,
    successTitleText: String,
    successSubtitleText: String,
    loadingSubtitleText: String,
    goBack: () -> Unit
) : BaseDataStateManager(context, dataStateBinding, successTitleText, successSubtitleText) {
    private val loadingUi = DataLoadingStateUi(context, dataStateBinding, loadingSubtitleText)
    private val dataAddStateManager = DataAddStateManager(dataStateBinding, errorUi, successUi, loadingUi, dialog, goBack)
    private val dataGetStateManager = DataGetStateManager(dataStateBinding, errorUi, dialog, goBack)

    init {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dataStateBinding.root)
    }

    fun observeGetState(resource: Resource) {
        dataGetStateManager.observeState(resource)
    }

    fun observeAddState(resource: AddResource) {
        dataAddStateManager.observeState(resource)
    }

}