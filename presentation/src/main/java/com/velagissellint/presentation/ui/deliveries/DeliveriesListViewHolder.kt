package com.velagissellint.presentation.ui.deliveries

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.velagissellint.presentation.R

class DeliveriesListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val nameOfProduct: TextView = view.findViewById(R.id.name_of_product_del)
    val ordered: TextView = view.findViewById(R.id.ordered)
    val keeping: TextView = view.findViewById(R.id.keeping)
}