package com.velagissellint.presentation.ui.deliveries.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.velagissellint.domain.models.Product
import com.velagissellint.presentation.R
import com.velagissellint.presentation.ui.deliveries.DeliveriesListDiffCallback
import com.velagissellint.presentation.ui.deliveries.DeliveriesListViewHolder
import com.velagissellint.presentation.ui.productList.ProductListDiffCallback
import com.velagissellint.presentation.ui.productList.ProductListViewHolder

class DeliveriesAdapter : PagingDataAdapter<Product, DeliveriesListViewHolder>(
    DeliveriesListDiffCallback()
) {
    override fun onBindViewHolder(holder: DeliveriesListViewHolder, position: Int) {
        holder.nameOfProduct.text = getItem(position)?.name
        holder.ordered.text = getItem(position)?.nextOrder.toString()
        holder.keeping.text= getItem(position)?.keeping.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveriesListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_deliveries,
            parent,
            false
        )
        return DeliveriesListViewHolder(view)
    }
}