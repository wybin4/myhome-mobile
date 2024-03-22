package com.example.myhome.models

import java.util.Date

interface UnixTimeConverter {
    fun unixStringToDate(unixTime: Long): Date {
        return Date(unixTime)
    }
}