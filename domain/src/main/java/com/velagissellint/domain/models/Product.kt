package com.velagissellint.domain.models

data class Product(
    var idCategory: String,
    var name: String,
    var price: Int,
    var inStock: Int,
    var soldInDay: Int,
    var keeping: Int,//хранение 0-обычное, 1-холодильник, 2-морозильник
    var expirationDate: String
) {
    constructor() : this("", "", 0, 0, 0, 0, "")
}
