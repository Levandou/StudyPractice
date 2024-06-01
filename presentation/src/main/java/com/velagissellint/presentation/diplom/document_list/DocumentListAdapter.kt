package com.velagissellint.presentation.diplom.document_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.velagissellint.domain.models.diplom.Document
import com.velagissellint.presentation.R

class DocumentListAdapter(
    private val onClick: (documentId: Int) -> Unit
) : ListAdapter<Document, DocumentViewHolder>(
    DocumentDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DocumentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_document,
            parent,
            false
        )
        return DocumentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val document: Document = getItem(position)
        holder.apply {
            tvDocumentName.text = document.title
            tvDocumentStatus.text = if (document.signed == true)
                "Подписан"
            else "Не подписан"
            val tvColor = if (document.signed == true)
                ContextCompat.getColor(view.context, R.color.green_light)
            else ContextCompat.getColor(view.context, R.color.red_accent)
            tvDocumentStatus.setTextColor(tvColor)

            tvDocumentDate.text = document.date

        }
        holder.view.setOnClickListener {
            document.id?.let { documentId ->
                onClick(documentId)
            }
        }
    }
}

class DocumentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvDocumentName = view.findViewById<TextView>(R.id.tv_document_name)
    val tvDocumentStatus = view.findViewById<TextView>(R.id.tv_document_status)
    val tvDocumentDate = view.findViewById<TextView>(R.id.tv_document_date)
}

class DocumentDiffCallback : DiffUtil.ItemCallback<Document>() {
    override fun areItemsTheSame(
        oldItem: Document,
        newItem: Document
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Document,
        newItem: Document
    ): Boolean = oldItem == newItem
}