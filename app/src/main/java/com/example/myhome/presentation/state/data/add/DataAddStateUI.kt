package com.example.myhome.presentation.state.data.add

import com.example.myhome.databinding.DataStateBinding

class DataAddStateUI(
    private val dataStateBinding: DataStateBinding,
    private val backBtnClick: () -> Unit
) {
    fun setupState() {
        dataStateBinding.apply {
            backButton.text = "Закрыть"
            backButton.setOnClickListener {
                backBtnClick()
            }
        }
    }
}
