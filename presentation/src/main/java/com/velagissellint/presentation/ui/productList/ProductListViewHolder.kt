package com.velagissellint.presentation.ui.productList

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.velagissellint.presentation.R

class ProductListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val nameOfProduct: TextView = view.findViewById(R.id.name_of_product)
    val price: TextView = view.findViewById(R.id.price)
}