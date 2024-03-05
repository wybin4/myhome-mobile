package com.example.myhome.presentation.state.data.get

import android.app.Dialog

class DataGetStateBehavior(
    private val dialog: Dialog,
    private val goBack: () -> Unit
) {
    fun setupBehavior() {
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun setupBackAction() {
        dialog.hide()
        goBack()
    }
}