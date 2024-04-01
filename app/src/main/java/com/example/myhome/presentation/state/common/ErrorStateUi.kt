package com.example.myhome.presentation.state.common

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.myhome.R
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.utils.pickers.ColorPicker

class ErrorStateUi(
    private val context: FragmentActivity,
    private val dataStateBinding: DataStateBinding
): ColorPicker {
    private fun getColor(color: Int): Int {
        return getColor(context, color)
    }

    fun showCodeErrorState() {
        dataStateBinding.apply {
            title.text = "Что-то пошло не так"
            subtitle.text = "Пожалуйста, попробуйте позже или обратитесь в службу поддержки для получения помощи"

            codeErrorImg.visibility = View.VISIBLE

            backButton.text = "Назад"
            backButton.setTextColor(getColor(R.color.red))
        }
    }

    fun showNetworkErrorState(message: String) {
        dataStateBinding.apply {
            errorCode.text = message
            errorCode.visibility = View.VISIBLE

            title.text = "Что-то пошло не так"
            subtitle.text = "Попробуйте перезагрузить телефон или подключиться к другой сети"

            networkErrorImg.visibility = View.VISIBLE

            backButton.text = "Назад"
            backButton.setTextColor(getColor(R.color.red))
        }
    }
}