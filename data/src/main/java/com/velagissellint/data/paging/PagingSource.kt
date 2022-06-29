package com.velagissellint.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.velagissellint.domain.models.Category
import kotlinx.coroutines.tasks.await

class PagingSource : PagingSource<QuerySnapshot, Category>() {
    private val db = FirebaseFirestore.getInstance()
    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Category> {
        return try {
            // Step 1
            val currentPage = params.key ?: db.collection("Category")
                .limit(20)
                .get()
                .await()
            // Step 2
            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

            // Step 3
            val nextPage = db.collection("Category").limit(20).startAfter(lastDocumentSnapshot)
                .get()
                .await()

            // Step 4
            LoadResult.Page(
                data = currentPage.toObjects(Category::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Category>): QuerySnapshot? {
        TODO("Not yet implemented")
    }
}