package com.velagissellint.presentation.ui.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.velagissellint.presentation.R

class CategoriesListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val nameOfCategory: TextView = view.findViewById(R.id.name_of_category)
}