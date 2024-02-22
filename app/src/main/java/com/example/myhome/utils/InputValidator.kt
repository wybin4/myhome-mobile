package com.example.myhome.utils

import com.google.android.material.textfield.TextInputLayout

class InputValidator(
    private val textInputLayout: TextInputLayout,
    private val validationCallback: ValidationCallback,
    private val errorMessage: String,
    private val errorReset: (() -> Unit)?
) {
    private var isFirstError: Boolean = true
    fun validate(text: String?): Boolean {
        val isValid = validationCallback(text)

        if (isValid) {
            textInputLayout.error = null
            isFirstError = true
        } else {
            textInputLayout.error = errorMessage
            if (isFirstError) {
                isFirstError = false
                errorReset?.let { it() }
            }
        }

        return isValid
    }


}

typealias ValidationCallback = (String?) -> Boolean
