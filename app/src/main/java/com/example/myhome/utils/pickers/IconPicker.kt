package com.example.myhome.utils.pickers

import com.example.myhome.R
import com.example.myhome.utils.models.TypeOfServiceEngNames

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
