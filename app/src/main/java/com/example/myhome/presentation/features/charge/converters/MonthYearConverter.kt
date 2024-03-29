package com.example.myhome.presentation.features.charge.converters

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface MonthYearConverter {
    private val dateFormatNormal: SimpleDateFormat
        get() = SimpleDateFormat("MMMM yyyy", Locale("ru"))

    private val dateFormatShort: SimpleDateFormat
        get() = SimpleDateFormat("MMM yyyy", Locale("ru"))

    companion object {
        private val monthReplacements = mapOf(
            "Января" to "Январь",
            "Февраля" to "Февраль",
            "Марта" to "Март",
            "Апреля" to "Апрель",
            "Мая" to "Май",
            "Июня" to "Июнь",
            "Июля" to "Июль",
            "Августа" to "Август",
            "Сентября" to "Сентябрь",
            "Октября" to "Октябрь",
            "Ноября" to "Ноябрь",
            "Декабря" to "Декабрь",
        )
    }

    fun formatDate(date: Date, capFirst: Boolean = true): String {
        var formattedDate = dateFormatNormal.format(date) ?: throw IllegalArgumentException("Неверный формат даты: $date")
        if (capFirst) {
            formattedDate = formattedDate.replaceFirstChar { it.uppercase() }
        }

        val firstSpaceIndex = formattedDate.indexOf(' ')
        return if (firstSpaceIndex != -1) {
            val firstWord = formattedDate.substring(0, firstSpaceIndex)
            val modifiedFirstWord = monthReplacements[firstWord] ?: firstWord

            modifiedFirstWord + formattedDate.substring(firstSpaceIndex)
        } else {
            formattedDate
        }
    }

    fun formatDateShort(date: Date): String {
        val formattedDate = dateFormatShort.format(date) ?: throw IllegalArgumentException("Неверный формат даты: $date")
        val splitDate = formattedDate.split(" ")
        val modifiedFirstWord = splitDate[0].uppercase().replace(".", "")
        val remainingPart = splitDate.subList(1, splitDate.size).joinToString(" ").replaceFirst(".", "\n")
        val lastIndex = modifiedFirstWord.lastIndex
        val lastChar = modifiedFirstWord[lastIndex]
        val modifiedLastWord = if (lastChar == 'Я') modifiedFirstWord.substring(0, lastIndex) + 'Й' else modifiedFirstWord
        return "$modifiedLastWord\n$remainingPart"
    }

}