package com.velagissellint.data.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.velagissellint.domain.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(
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
            userInfo["firstName"] = user.firstName
            userInfo["secondName"] = user.secondName
            userInfo["phone"] = user.phone
            userInfo["email"] = user.email
            userInfo["adm"] = 0
            df.set(userInfo)
            Toast.makeText(context, "Регистрация прошла успешна", Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(context, "Ошибка регистрации", Toast.LENGTH_LONG).show()
    }

    fun logIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Вход прошёл успешно",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Ошибка входа",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}