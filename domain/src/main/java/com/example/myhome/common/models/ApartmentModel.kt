package com.example.myhome.common.models

data class ApartmentGetModel (
    override val id: Int,
    override val name: String,
    val subscriberId: Int
): Identifiable