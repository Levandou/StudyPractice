package com.velagissellint.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.models.diplom.Team
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.user.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ProfileRepositoryFb : ProfileRepository {
    override fun getProfile(profileId: String): MutableStateFlow<RequestResult<Profile>> =
        MutableStateFlow<RequestResult<Profile>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Profile")

            collectionRef
                .whereEqualTo("id", profileId)
                .get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents.getOrNull(0)
                    result.value = RequestResult.Success(
                        Profile(
                            selectedDocument?.getString("id"),
                            selectedDocument?.getString("fullName"),
                            selectedDocument?.getString("position"),
                            (selectedDocument?.getLong("teamId") ?: "").toString().toIntOrNull(),
                            selectedDocument?.getBoolean("isActive") ?: false,
                            selectedDocument?.getBoolean("isAdmin") ?: false
                        )
                    )
                }
                .addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }
        }

    override fun getProfileName(profileId: String?): MutableStateFlow<RequestResult<Pair<String, String>>> =
        MutableStateFlow<RequestResult<Pair<String, String>>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Profile")

            profileId?.let {
                collectionRef
                    .whereEqualTo("id", profileId)
                    .get()
                    .addOnSuccessListener { documents ->
                        val selectedDocument = documents.documents.getOrNull(0)
                        val fullName = selectedDocument?.getString("fullName")
                        result.value = RequestResult.Success(
                            Pair(profileId, fullName.orEmpty()),
                        )
                    }
                    .addOnFailureListener {
                        result.value = RequestResult.Error("addOnFailureListener")
                    }
            } ?: {
                result.value = RequestResult.Error("addOnFailureListener")
            }
        }

    override fun getProfileFromName(name: String): MutableStateFlow<RequestResult<Profile>> =
        MutableStateFlow<RequestResult<Profile>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Profile")

            collectionRef
                .whereEqualTo("fullName", name)
                .get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents.getOrNull(0)
                    result.value = RequestResult.Success(
                        Profile(
                            selectedDocument?.getString("id"),
                            selectedDocument?.getString("fullName"),
                            selectedDocument?.getString("position"),
                            (selectedDocument?.getLong("teamId") ?: "").toString().toIntOrNull(),
                            selectedDocument?.getBoolean("isAdmin") ?: false,
                            selectedDocument?.getBoolean("isActive") ?: false
                        )
                    )
                }
                .addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }
        }

    override fun getAllProfiles(): MutableStateFlow<RequestResult<List<Profile>>> =
        MutableStateFlow<RequestResult<List<Profile>>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Profile")

            collectionRef
                //.whereEqualTo("teamId", teamId)
                .get()
                .addOnSuccessListener { documents ->
                    val listProfiles = documents.documents.map {
                        Profile(
                            it?.getString("id"),
                            it?.getString("fullName"),
                            it?.getString("position"),
                            (it?.getLong("teamId") ?: "").toString().toIntOrNull(),
                            it?.getBoolean("isAdmin") ?: false,
                            it?.getBoolean("isActive") ?: false
                        )
                    }

                    result.update {
                        RequestResult.Success(listProfiles)
                    }
                }
                .addOnFailureListener {
                    result.update {
                        RequestResult.Error("addOnFailureListener")
                    }
                }
        }
}