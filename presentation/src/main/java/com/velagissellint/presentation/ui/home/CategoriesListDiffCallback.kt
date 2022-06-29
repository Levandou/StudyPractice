package com.velagissellint.presentation.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.velagissellint.domain.models.Category

class CategoriesListDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category) =
        oldItem.nameOfCategory == newItem.nameOfCategory

    override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
}