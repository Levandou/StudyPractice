package com.velagissellint.domain.models.diplom

data class Chart(
    val id: Int?,
    val profileId: String,
    val startDate: String,
    val mo: Int,
    val tu: Int,
    val we: Int,
    val th: Int,
    val fr: Int
)
