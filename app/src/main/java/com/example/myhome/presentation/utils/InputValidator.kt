package com.example.myhome.presentation.utils

import android.content.res.ColorStateList
import com.google.android.material.textfield.TextInputLayout

class InputValidator(
    private val textInputLayout: TextInputLayout,
    private val validationCallback: ValidationCallback,
    private val errorMessage: String,
    private val errorReset: (() -> Unit)?,
    errorColor: Int? = null
) {
    private var isFirstError: Boolean = true

    init {
        if (errorColor != null) {
            textInputLayout.setErrorTextColor(ColorStateList.valueOf(errorColor))
        }
    }

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
