package com.velagissellint.presentation.diplom.task_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.velagissellint.domain.models.diplom.Task
import com.velagissellint.presentation.R

class TaskListAdapter(
    private val onClick: (taskId: Int) -> Unit
) : ListAdapter<Task, TaskViewHolder>(
    TaskDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_task,
            parent,
            false
        )
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = getItem(position)
        holder.apply {
            tvName.text = task.title.orEmpty()
            tvStatus.text = task.status.orEmpty()
            tvDate.text = task.finishTime.orEmpty()
            tvAssigned.text = task.assignedName
            tvReviewer.text = task.reviewerName
            tvAuthor.text = task.authorName
        }
        holder.view.setOnClickListener {
            task.id?.let { taskId ->
                onClick(taskId)
            }
        }
    }
}

class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.findViewById<TextView>(R.id.tv_task_name)
    val tvAssigned = view.findViewById<TextView>(R.id.tv_name_assigned)
    val tvReviewer = view.findViewById<TextView>(R.id.tv_name_reviewer)
    val tvAuthor = view.findViewById<TextView>(R.id.tv_name_author)

    val tvStatus = view.findViewById<TextView>(R.id.tv_document_status)
    val tvDate = view.findViewById<TextView>(R.id.tv_document_date)
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(
        oldItem: Task,
        newItem: Task
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Task,
        newItem: Task
    ): Boolean = oldItem == newItem
}