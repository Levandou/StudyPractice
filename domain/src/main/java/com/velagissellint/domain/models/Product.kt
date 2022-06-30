package com.velagissellint.domain.models

data class Product(
    var idCategory: String,
    var name: String,
    var price: Int,
    var inStock: MutableList<Int>,
    var soldInDay: MutableList<Int>,
    var keeping: Int,//хранение 0-обычное, 1-холодильник, 2-морозильник
    var expirationDate: Int,
    var dateOfManufacture: MutableList<String>,
    var volume: Double,
    var nextOrder: Int

) {
    constructor() : this(
        "",
        "",
        0,
        mutableListOf(),
        mutableListOf(),
        0,
        0,
        mutableListOf(),
        0.0,
        0
    )
}
