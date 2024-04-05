package com.example.myhome.presentation.models

interface Adaptive {
    fun areContentsTheSame(other: Adaptive): Boolean {
        return this == other
    }
}

interface AdaptiveInt: Adaptive {
    val id: Int
}

interface AdaptiveString: Adaptive {
    val id: String
}
