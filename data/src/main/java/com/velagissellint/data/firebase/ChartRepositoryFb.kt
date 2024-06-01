package com.velagissellint.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.velagissellint.domain.models.diplom.Chart
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.chart.ChartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ChartRepositoryFb : ChartRepository {
    override fun createChart(
        profileId: String,
        mo: Int,
        tu: Int,
        we: Int,
        th: Int,
        fr: Int
    ) {
        val fStore = FirebaseFirestore.getInstance()
        val collectionRef = fStore.collection("Chart")

        val calendar = Calendar.getInstance()
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val daysToMonday = (Calendar.MONDAY + 7 - currentDayOfWeek) % 7
        calendar.add(Calendar.DAY_OF_MONTH, daysToMonday)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val startDate = dateFormat.format(calendar.time)

        val query = collectionRef.orderBy("id", Query.Direction.DESCENDING).limit(1)
        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val lastAddedItem = querySnapshot.documents[0]
                val idLast = lastAddedItem.get("id").toString().toInt()

                val newTask = hashMapOf(
                    "id" to idLast,
                    "profileId" to profileId,
                    "startDate" to startDate,
                    "mo" to mo,
                    "tu" to tu,
                    "we" to we,
                    "th" to th,
                    "fr" to fr
                )
                fStore.collection("Chart").document().set(newTask)
            }
        }
    }

    override fun getCurrentChart(profileId: String): MutableStateFlow<RequestResult<Chart>> =
        MutableStateFlow<RequestResult<Chart>>(RequestResult.Loading).also { result ->
            val calendar = Calendar.getInstance()
            val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val dayOffset = if (currentDayOfWeek == Calendar.SUNDAY) 1 else 2
            calendar.add(Calendar.DAY_OF_WEEK, -(currentDayOfWeek + dayOffset - Calendar.MONDAY))
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val startDate = dateFormat.format(calendar.time)

            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Profile")

            collectionRef
                .whereEqualTo("profileId", profileId)
                .whereEqualTo("startDate", startDate)
                .get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents.getOrNull(0)
                    result.value = RequestResult.Success(
                        Chart(
                            id = selectedDocument?.getLong("id")?.toInt(),
                            profileId = profileId,
                            startDate = startDate,
                            mo = selectedDocument?.getLong("mo")?.toInt() ?: 0,
                            tu = selectedDocument?.getLong("tu")?.toInt() ?: 0,
                            we = selectedDocument?.getLong("we")?.toInt() ?: 0,
                            th = selectedDocument?.getLong("th")?.toInt() ?: 0,
                            fr = selectedDocument?.getLong("fr")?.toInt() ?: 0
                        )
                    )
                }
                .addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }
        }

    override fun getNextChart(profileId: String): MutableStateFlow<RequestResult<Chart>> =
        MutableStateFlow<RequestResult<Chart>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Chart")

            val calendar = Calendar.getInstance()
            val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val daysToMonday = (Calendar.MONDAY + 7 - currentDayOfWeek) % 7
            calendar.add(Calendar.DAY_OF_MONTH, daysToMonday)
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val startDate = dateFormat.format(calendar.time)

            collectionRef
                .whereEqualTo("profileId", profileId)
                .whereEqualTo("startDate", startDate)
                .get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents.getOrNull(0)
                    result.value = RequestResult.Success(
                        Chart(
                            id = selectedDocument?.getLong("id")?.toInt(),
                            profileId = profileId,
                            startDate = startDate,
                            mo = selectedDocument?.getLong("mo")?.toInt() ?: 0,
                            tu = selectedDocument?.getLong("tu")?.toInt() ?: 0,
                            we = selectedDocument?.getLong("we")?.toInt() ?: 0,
                            th = selectedDocument?.getLong("th")?.toInt() ?: 0,
                            fr = selectedDocument?.getLong("fr")?.toInt() ?: 0
                        )
                    )
                }
                .addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }
        }

    override fun isNextChartSelected(profileId: String): MutableStateFlow<RequestResult<Boolean>> =
        MutableStateFlow<RequestResult<Boolean>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Chart")

            val calendar = Calendar.getInstance()
            val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val daysToMonday = (Calendar.MONDAY + 7 - currentDayOfWeek) % 7
            calendar.add(Calendar.DAY_OF_MONTH, daysToMonday)
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val startDate = dateFormat.format(calendar.time)

            collectionRef
                .whereEqualTo("profileId", profileId)
                .whereEqualTo("startDate", startDate)
                .get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents.getOrNull(0)
                    val value = selectedDocument?.getLong("id")?.toInt() != null
                    result.value = RequestResult.Success(value)
                }
                .addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }

        }
}