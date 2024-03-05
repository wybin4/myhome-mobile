package com.example.myhome.presentation.features.common.models

import com.example.myhome.presentation.models.Selectable

data class ApartmentExtendedUiModel (
    override val id: Int,
    override val name: String,
    val subscriberId: Int
): Selectable