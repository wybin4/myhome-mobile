package com.example.myhome.presentation.state.add

import android.app.Dialog

class AddStateBehavior(
    private val dialog: Dialog,
    private val goBack: () -> Unit
) {
    fun setupVisibility(resource: AddState) {
        if (resource !is AddState.None) {
            dialog.show()
        }
    }

    fun setupNotLoadingBehavior() {
        dialog.setCanceledOnTouchOutside(true)
        goBack()
    }

    fun setupLoadingBehavior() {
        dialog.setCanceledOnTouchOutside(false)
    }

    fun setupBackAction() {
        dialog.hide()
    }
}