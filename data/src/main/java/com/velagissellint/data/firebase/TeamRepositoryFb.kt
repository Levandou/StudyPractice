package com.velagissellint.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.velagissellint.domain.models.diplom.Team
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.team.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TeamRepositoryFb : TeamRepository {
    override fun getTeam(teamId: Int): MutableStateFlow<RequestResult<Team>> =
        MutableStateFlow<RequestResult<Team>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Team")

            collectionRef
                .whereEqualTo("id", teamId)
                .get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents.getOrNull(0)
                    result.update {
                        RequestResult.Success(
                            Team(
                                id = selectedDocument?.getLong("id")?.toInt(),
                                name = selectedDocument?.getString("name")
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

    override fun addTeam(teamName: String) {
        val fStore = FirebaseFirestore.getInstance()
        val collectionRef = fStore.collection("Team")

        val query = collectionRef.orderBy("id", Query.Direction.DESCENDING).limit(1)
        var idLast = 0
        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val lastAddedItem = querySnapshot.documents.getOrNull(0)
                idLast = lastAddedItem?.get("id").toString().toInt()
            }
        }

        val newTask = hashMapOf(
            "id" to idLast,
            "name" to teamName,
        )
        fStore.collection("Team").document().set(newTask)
    }

    override fun getAllTeams(): MutableStateFlow<RequestResult<List<Team>>> =
        MutableStateFlow<RequestResult<List<Team>>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Team")

            collectionRef.get()
                .addOnSuccessListener { documents ->
                    val listTeam = documents.documents.map {
                        Team(
                            id = it?.getLong("id")?.toInt(),
                            name = it?.getString("name")
                        )
                    }

                    result.update {
                        RequestResult.Success(listTeam)
                    }
                }
                .addOnFailureListener {
                    result.update {
                        RequestResult.Error("addOnFailureListener")
                    }
                }
        }
}