package com.example.myhome.presentation.utils.pickers

interface ProportionPicker {
    fun calculateProportion(numberOfVotes: Int, totalVotes: Int): Double {
        return (numberOfVotes.toDouble() / totalVotes.toDouble()) * 100
    }
}