package com.velagissellint.domain.models

data class User(
    var email: String,
    var firstName: String,
    var phone: String,
    var secondName: String,
    var adm: Int,
) {
    constructor() : this(
        email = "",
        firstName = "",
        phone = "", /*isAdmin = "",*/
        secondName = "",
        0
    )
}