package com.velagissellint.domain.models

data class Category(
    var nameOfCategory: String,
    var id: String
) {
    constructor() : this("", "")
}
