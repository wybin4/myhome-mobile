package com.example.myhome.utils.managers.state.data

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentActivity
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.utils.managers.state.data.common.DataErrorStateUi
import com.example.myhome.utils.managers.state.data.common.DataSuccessStateUi

abstract class BaseDataStateManager(
    context: FragmentActivity,
    dataStateBinding: DataStateBinding,
    successTitleText: String,
    successSubtitleText: String
) {
    protected val dialog = Dialog(context)
    protected val errorUi = DataErrorStateUi(context, dataStateBinding)
    protected val successUi = DataSuccessStateUi(context, dataStateBinding, successTitleText, successSubtitleText)

    init {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dataStateBinding.root)
    }

}