package com.example.myhome.presentation.features.chat

interface MessageFormatter {
    fun formatCountUnread(countUnread: Int): String {
        return if (countUnread < 100) {
            countUnread.toString()
        } else "99+"
    }
}