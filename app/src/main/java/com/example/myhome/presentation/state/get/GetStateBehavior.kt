package com.example.myhome.presentation.state.get

import android.app.Dialog

class GetStateBehavior(
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