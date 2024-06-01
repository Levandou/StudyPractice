package com.velagissellint.domain.models.diplom

data class Profile(
    val id: String?,
    val fullName: String?,
    val position: String?,
    val teamId: Int?,
    val isActive: Boolean,
    val isAdmin: Boolean = false,
    val teamName: String = "",
)
