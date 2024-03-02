package com.example.myhome.utils.managers.state.data.common

import android.view.View
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.utils.pickers.ColorPicker

class DataLoadingStateUi(
    private val dataStateBinding: DataStateBinding
): ColorPicker {
    fun showNotLoadingState() {
        dataStateBinding.apply {
            notLoading.visibility = View.VISIBLE
            onLoading.visibility = View.GONE
        }
    }

    fun showLoadingState() {
        dataStateBinding.apply {
            notLoading.visibility = View.GONE
            onLoading.visibility = View.VISIBLE
        }
    }
}