package com.velagissellint.data.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.models.diplom.Task
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.task.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow

class TaskRepositoryFb : TaskRepository {

    fun createTask(
        teamId: Int,
        assignedId: String?,
        status: String,
        finishTime: String,
        title: String,
        description: String,
        reviewerId: String?,
        authorId: String
    ) {
        val fStore = FirebaseFirestore.getInstance()
        val collectionRef = fStore.collection("Task")

        val query = collectionRef.orderBy("id", Query.Direction.DESCENDING).limit(1)
        var idLast: Int = 0
        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val lastAddedItem = querySnapshot.documents[0]
                idLast = lastAddedItem.get("id").toString().toInt()
            }
        }

        val newTask = hashMapOf(
            "id" to idLast,
            "teamId" to teamId,
            "assignedId" to assignedId,
            "status" to status,
            "finishTime" to finishTime,
            "title" to title,
            "description" to description,
            "reviewerId" to reviewerId,
            "authorId" to authorId
        )
        fStore.collection("Task").document().set(newTask)
    }

    fun updateTask(
        id: Int,
        teamId: Int,
        assignedId: String?,
        status: String,
        finishTime: String,
        title: String,
        description: String,
        reviewerId: String?,
        authorId: String
    ) {
        val fStore = FirebaseFirestore.getInstance()
        val collectionRef = fStore.collection("Task")

        collectionRef.whereEqualTo("id", id).get().addOnSuccessListener { documents ->
            val selectedDocument = documents.documents[0]
            val ref = selectedDocument.reference
            val newTask: HashMap<String, *> = hashMapOf(
                "id" to id,
                "teamId" to teamId,
                "assignedId" to assignedId,
                "status" to status,
                "finishTime" to finishTime,
                "title" to title,
                "description" to description,
                "reviewerId" to reviewerId,
                "authorId" to authorId
            )
            ref.update(newTask)
        }
    }

    fun deleteTask(id: Int) {
        val fStore = FirebaseFirestore.getInstance()
        val collectionRef = fStore.collection("Task")

        collectionRef.whereEqualTo("id", id).get().addOnSuccessListener { documents ->
            val selectedDocument = documents.documents[0]
            val ref = selectedDocument.reference
            ref.delete()
        }
    }

    override fun getTaskList(profileId: String): MutableStateFlow<RequestResult<List<Task>>> =
        MutableStateFlow<RequestResult<List<Task>>>(RequestResult.Loading).also { result ->
            val query = FirebaseFirestore.getInstance().collection("Task")
                .whereEqualTo("AuthorId", profileId)
            val query2 = FirebaseFirestore.getInstance().collection("Task")
                .whereEqualTo("AssignedId", profileId)
            val query3 = FirebaseFirestore.getInstance().collection("Task")
                .whereEqualTo("ReviewerId", profileId)
            val tasks = arrayListOf<com.google.android.gms.tasks.Task<QuerySnapshot>>()
            tasks.add(query.get())
            tasks.add(query2.get())
            tasks.add(query3.get())
            Tasks.whenAllComplete(tasks).addOnSuccessListener {
                val results = arrayListOf<DocumentSnapshot>()
                for (task in tasks) {
                    if (task.isSuccessful) {
                        val snapshot = task.result
                        results.addAll(snapshot.documents)
                    }
                }
                val distinctResults = results.distinctBy { it.getLong("id") }

                val newListTasks = distinctResults.map {
                    Task(
                        id = it.getLong("id")?.toInt(),
                        teamId = it.getLong("TeamId")?.toInt(),
                        assignedId = it.getString("AssignedId"),
                        status = it.getString("Status"),
                        finishTime = it.getString("FinishTime"),
                        title = it.getString("Title"),
                        description = it.getString("Description"),
                        reviewerId = it.getString("ReviewerId"),
                        authorId = it.getString("AuthorId")
                    )
                }
                result.value = RequestResult.Success(newListTasks)
            }
                .addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }
        }

    override fun getTask(taskId: Int): MutableStateFlow<RequestResult<Task>> =
        MutableStateFlow<RequestResult<Task>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Task")

            collectionRef
                .whereEqualTo("id", taskId)
                .get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents.getOrNull(0)
                    result.value = RequestResult.Success(
                        Task(
                            id = selectedDocument?.getLong("id")?.toInt(),
                            teamId = selectedDocument?.getLong("TeamId")?.toInt(),
                            assignedId = selectedDocument?.getString("AssignedId"),
                            status = selectedDocument?.getString("Status"),
                            finishTime = selectedDocument?.getString("FinishTime"),
                            title = selectedDocument?.getString("Title"),
                            description = selectedDocument?.getString("Description"),
                            reviewerId = selectedDocument?.getString("ReviewerId"),
                            authorId = selectedDocument?.getString("AuthorId")
                        )
                    )
                }
                .addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }
        }
}