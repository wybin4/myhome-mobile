package com.example.myhome.presentation.state.data.common

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.myhome.R
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.utils.pickers.ColorPicker

class DataSuccessStateUi(
    private val context: FragmentActivity,
    private val dataStateBinding: DataStateBinding,
    private val titleText: String,
    private val subtitleText: String
): ColorPicker {
    private fun getColor(color: Int): Int {
        return getColor(context, color)
    }

    fun showSuccessState() {
        dataStateBinding.apply {
            title.text = titleText
            subtitle.text = subtitleText

            successImg.visibility = View.VISIBLE

            backButton.text = "Закрыть"
            backButton.setTextColor(getColor(R.color.green))
        }
    }
}