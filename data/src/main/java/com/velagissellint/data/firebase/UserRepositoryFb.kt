package com.velagissellint.data.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.velagissellint.domain.models.User
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class UserRepositoryFb(
    private val context: Context
) {
    fun createAccount(
        email: String,
        password: String,
        user: User
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                addUserToFireStore(user, it.isSuccessful)
            }.addOnFailureListener {
                Log.d("asd", it.toString())
            }
    }

    fun addUserToFireStore(user: User, boolean: Boolean) {
        if (boolean) {
            val auth = FirebaseAuth.getInstance()
            val fStore = FirebaseFirestore.getInstance()
            val fireUser: FirebaseUser = auth.currentUser!!
            val df = fStore.collection("Users").document(fireUser.uid)
            val userInfo: MutableMap<String, Any> = mutableMapOf()
            /*       userInfo["firstName"] = user.firstName
                   userInfo["secondName"] = user.secondName
                   userInfo["phone"] = user.phone*/
            userInfo["email"] = user.email
            userInfo["adm"] = 0
            df.set(userInfo)
            Toast.makeText(context, "Регистрация прошла успешна", Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(context, "Ошибка регистрации", Toast.LENGTH_LONG).show()
    }

    fun logIn(email: String, password: String): MutableStateFlow<RequestResult<Boolean>> =
        MutableStateFlow<RequestResult<Boolean>>(RequestResult.Loading).also { result ->
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        result.value = RequestResult.Success(true)
                    } else {
                        result.update { RequestResult.Error(task.exception?.message.orEmpty()) }
                    }
                }
                .addOnFailureListener {
                    result.update { RequestResult.Error(it.toString()) }
                }
        }

    fun getProfile(profileId: String): MutableStateFlow<RequestResult<Profile>> {
        val fStore = FirebaseFirestore.getInstance()
        val collectionRef = fStore.collection("Profile")

        return MutableStateFlow<RequestResult<Profile>>(RequestResult.Loading).also { result ->
            collectionRef
                .whereEqualTo("profileId", profileId)
                .get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents[0]
                    result.update {
                        RequestResult.Success(
                            Profile(
                                selectedDocument.getString("id"),
                                selectedDocument.getString("fullName"),
                                selectedDocument.getString("position"),
                                selectedDocument.getLong("teamId")?.toInt(),
                                selectedDocument.getBoolean("isActive") ?: false,
                                isAdmin = selectedDocument.getBoolean("isAdmin") ?: false
                            )
                        )
                    }
                }
                .addOnFailureListener {
                    result.update {
                        RequestResult.Error("addOnFailureListener")
                    }
                }
        }
    }
}