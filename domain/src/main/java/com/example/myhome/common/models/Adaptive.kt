package com.example.myhome.common.models

interface Adaptive {
    val id: Int
    fun areContentsTheSame(other: Adaptive): Boolean {
        return this == other
    }
}
