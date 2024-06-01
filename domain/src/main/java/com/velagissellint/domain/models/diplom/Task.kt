package com.velagissellint.domain.models.diplom

data class Task(
    val id: Int?,
    val teamId: Int?,
    val assignedId: String?,
    val status: String?,
    val finishTime: String?,
    val title: String?,
    val description: String?,
    val reviewerId: String?,
    val authorId: String?,
    val authorName: String = "",
    val assignedName: String = "",
    val reviewerName: String = ""
)
