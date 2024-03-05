package com.example.myhome.presentation.models

interface Adaptive {
    val id: Int
    fun areContentsTheSame(other: Adaptive): Boolean {
        return this == other
    }
}
