package com.velagissellint.domain.models.diplom

data class Document(
    val id: Int?,
    val profileId: String?,
    val signed: Boolean?,
    val title: String?,
    val fullName: String?,
    val date: String?
)