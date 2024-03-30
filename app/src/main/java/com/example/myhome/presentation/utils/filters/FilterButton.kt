package com.example.myhome.presentation.utils.filters

import com.google.android.material.button.MaterialButton

class FilterButton<T : Enum<T>>(
    private val buttonActive: MaterialButton,
    private val buttonNotActive: MaterialButton,
    private val setStatusList: (List<T>) -> Unit,
    private val getStatusList: () -> List<T>,
    private val status: T
) {

    init {
        buttonActive.setOnClickListener {
            val currentStatusList = getStatusList()
            if (currentStatusList.size > 1 && currentStatusList.contains(status)) {
                val updatedStatusList = currentStatusList.toMutableList()
                updatedStatusList.remove(status)
                setStatusList(updatedStatusList)
                buttonActive.toggleVisibility(false)
                buttonNotActive.toggleVisibility(true)
            }
        }
        buttonNotActive.setOnClickListener {
            val currentStatusList = getStatusList()
            if (!currentStatusList.contains(status)) {
                val updatedStatusList = currentStatusList.toMutableList()
                updatedStatusList.add(status)
                setStatusList(updatedStatusList)
            }
            buttonActive.toggleVisibility(true)
            buttonNotActive.toggleVisibility(false)
        }
    }

    private fun MaterialButton.toggleVisibility(isVisible: Boolean) {
        visibility = if (isVisible) {
            MaterialButton.VISIBLE
        } else {
            MaterialButton.GONE
        }
    }
}
