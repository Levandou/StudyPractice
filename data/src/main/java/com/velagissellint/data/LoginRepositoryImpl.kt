package com.velagissellint.data

import com.google.firebase.firestore.FirebaseFirestore
import com.velagissellint.data.db.UserDao
import com.velagissellint.data.firebase.UserRepositoryFb
import com.velagissellint.domain.models.User
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.logIn.LogInRepository
import kotlinx.coroutines.flow.MutableStateFlow

class LoginRepositoryImpl(
    private val userRepositoryFb: UserRepositoryFb,
    private val userDao: UserDao
) : LogInRepository {
    override fun logIn(email: String, password: String) = userRepositoryFb.logIn(email, password)

    override fun saveUser(user: User) {
        userDao.saveUser(user)
    }

    override fun update(user: User) {
        userDao.updateUse(user)
    }

    override fun loadIsAdmin(email: String): MutableStateFlow<RequestResult<Boolean>> =
        MutableStateFlow<RequestResult<Boolean>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Profile")

            collectionRef
                .whereEqualTo("id", email)
                .get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents.getOrNull(0)
                    result.value = RequestResult.Success(
                        selectedDocument?.getBoolean("isAdmin") ?: false
                    )
                }
                .addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }
        }
}