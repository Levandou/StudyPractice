package com.velagissellint.presentation.ui.deliveries

import androidx.recyclerview.widget.DiffUtil
import com.velagissellint.domain.models.Product

class DeliveriesListDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}