package com.example.myhome.presentation.features.common.models

import com.example.myhome.presentation.models.AdaptiveInt
import com.example.myhome.presentation.models.Selectable

enum class TypeOfServiceEngNames {
    Gas, Electricity, Water, Heat
}

class TypeOfServiceUiModel (
    override val id: Int,
    override val name: String
): AdaptiveInt, Selectable