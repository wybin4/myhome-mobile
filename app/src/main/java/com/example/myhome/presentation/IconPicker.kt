package com.example.myhome.presentation

import com.example.myhome.R
import com.example.myhome.presentation.models.TypeOfServiceEngNames

interface IconPicker {
    fun getMeterIcon(name: String): Int? {
        return when (name.replaceFirstChar { it.uppercaseChar() }) {
            TypeOfServiceEngNames.Gas.toString() -> R.drawable.gas
            TypeOfServiceEngNames.Electricity.toString() -> R.drawable.electricity
            TypeOfServiceEngNames.Heat.toString() -> R.drawable.heat
            TypeOfServiceEngNames.Water.toString() -> R.drawable.water
            else -> null
        }
    }
}
