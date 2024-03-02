package com.example.myhome.utils.managers.state.data.add

import android.app.Dialog
import com.example.myhome.utils.models.AddResource

class DataAddStateBehavior(
    private val dialog: Dialog,
    private val goBack: () -> Unit
) {
    fun setupVisibility(resource: AddResource) {
        if (resource !is AddResource.None) {
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