package com.example.myhome.presentation.utils.pickers

interface CharPicker {
    fun processString(input: String): String {
        val cleanString = input.replace(Regex("[^A-Za-zА-Яа-я ]"), "")
        val firstLetter = cleanString.firstOrNull()?.toString() ?: ""
        val secondLetter = cleanString.substringAfter(" ", "").firstOrNull()?.toString() ?: ""
        return firstLetter + secondLetter
    }
}