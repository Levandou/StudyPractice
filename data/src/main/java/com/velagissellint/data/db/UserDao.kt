package com.velagissellint.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.velagissellint.domain.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users LIMIT 1 ")
    fun getCurrentUser(): User?

    @Insert(entity = User::class)
    fun saveUser(user: User)

    @Update(entity = User::class)
    fun updateUse(user: User)
}