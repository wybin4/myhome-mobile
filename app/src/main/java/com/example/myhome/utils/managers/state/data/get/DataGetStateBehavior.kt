package com.example.myhome.utils.managers.state.data.get

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