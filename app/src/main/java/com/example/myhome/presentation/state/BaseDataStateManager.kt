package com.example.myhome.presentation.state

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.state.common.ErrorStateUi
import com.example.myhome.presentation.state.common.SuccessStateUi

abstract class BaseDataStateManager(
    context: FragmentActivity,
    dataStateBinding: DataStateBinding,
    successTitleText: String,
    successSubtitleText: String
) {
    protected val dialog = Dialog(context)
    protected val errorUi = ErrorStateUi(context, dataStateBinding)
    protected val successUi = SuccessStateUi(context, dataStateBinding, successTitleText, successSubtitleText)

    init {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dataStateBinding.root)
    }

}