package com.velagissellint.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.velagissellint.domain.models.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao(): UserDao

    companion object {
        const val DB_NAME = "cases.db"
    }
}