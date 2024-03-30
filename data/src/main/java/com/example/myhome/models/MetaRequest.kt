package com.example.myhome.models

data class MetaRequest(
    val page: Int,
    val limit: Int,
    val filters: List<FilterListItemRequest>? = null
)

data class FilterListItemRequest(
    val filterField: String,
    var filterArray: List<String>
)