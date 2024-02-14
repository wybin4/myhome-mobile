package com.example.myhome.presentation.models

import com.example.myhome.common.models.Adaptive

class ApartmentUiModel (
    override val id: Int,
    val name: String,
    var selected: Boolean
): Adaptive {
    fun setSelected(selectedId: Int): ApartmentUiModel {
        selected = selectedId == id
        return this
    }
}