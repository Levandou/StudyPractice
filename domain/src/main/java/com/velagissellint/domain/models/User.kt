package com.velagissellint.domain.models

data class User(
    var email: String,
    var firstName: String,
    var phone: String,
    var secondName: String,
    var adm: Int,
) {
    constructor() : this(
        email = "as",
        firstName = "as",
        phone = "as", /*isAdmin = "",*/
        secondName = "as",
        0
    )
}