package com.example.myhome.utils.managers.state.data.common

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.myhome.R
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.utils.pickers.ColorPicker

class DataLoadingStateUi(
    private val context: FragmentActivity,
    private val dataStateBinding: DataStateBinding,
    private val subtitleString: String
): ColorPicker {
    private fun getColor(color: Int): Int {
        return getColor(context, color)
    }

    fun showLoadingState() {
        dataStateBinding.apply {
            title.text = "Ожидайте..."
            subtitle.text = subtitleString

            loadingImg.visibility = View.VISIBLE

            backButton.text = "Назад"
            backButton.setTextColor(getColor(R.color.primary))
        }
    }

    fun showNotLoadingState() {
        dataStateBinding.apply {
           loadingImg.visibility = View.GONE
        }
    }
}