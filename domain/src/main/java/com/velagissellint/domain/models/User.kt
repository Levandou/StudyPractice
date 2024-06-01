package com.velagissellint.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    var email: String,
    var isAdmin: Boolean
)