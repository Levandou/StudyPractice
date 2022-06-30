package com.velagissellint.presentation.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.velagissellint.domain.models.Category
import com.velagissellint.presentation.R
import com.velagissellint.presentation.ui.home.CategoriesListDiffCallback
import com.velagissellint.presentation.ui.home.CategoriesListViewHolder

class CategoriesListAdapter(
    private val clickOnItem: (id: String) -> Unit
) : PagingDataAdapter<Category, CategoriesListViewHolder>(
    CategoriesListDiffCallback()
) {
    override fun onBindViewHolder(holder: CategoriesListViewHolder, position: Int) {
        holder.nameOfCategory.text = getItem(position)?.nameOfCategory
        holder.itemView.setOnClickListener {
            clickOnItem(getItem(position)?.id.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_category,
            parent,
            false
        )
        return CategoriesListViewHolder(view)
    }
}