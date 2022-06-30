package com.velagissellint.presentation.ui.productList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.velagissellint.domain.models.Category
import com.velagissellint.domain.models.Product
import com.velagissellint.presentation.R
import com.velagissellint.presentation.ui.home.CategoriesListDiffCallback
import com.velagissellint.presentation.ui.home.CategoriesListViewHolder
import com.velagissellint.presentation.ui.productList.ProductListDiffCallback
import com.velagissellint.presentation.ui.productList.ProductListViewHolder

class ProductListAdapter : PagingDataAdapter<Product, ProductListViewHolder>(
    ProductListDiffCallback()
) {
    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.nameOfProduct.text = getItem(position)?.name
        holder.price.text = getItem(position)?.price.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent,
            false
        )
        return ProductListViewHolder(view)
    }
}