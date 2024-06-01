package com.velagissellint.presentation.diplom.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.databinding.FragmentTaskBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskFragment : Fragment() {
    private val argumentId by lazy {
        arguments?.getInt(TASK_ID)
    }

    private val viewModel by viewModel<TaskViewModel>()
    private var _binding: FragmentTaskBinding? = null
    private val binding: FragmentTaskBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        argumentId?.let {
            viewModel.getTask(it)
        }

        observeTasks()
        observeTasksWithNames()
    }

    private fun observeTasks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.taskFlow.collectLatest {
                it?.let { taskNotNull ->
                    viewModel.getWithNames(taskNotNull)
                }
            }
        }
    }

    private fun observeTasksWithNames() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.taskWithNames.collectLatest {
                if (it is RequestResult.Success) {
                    val task = it.data

                    binding.tvTaskName.text = task.title.orEmpty()
                    binding.tvDescription.text = task.description.orEmpty()
                    binding.tvAssigned.text = task.assignedName
                    binding.tvAuthor.text = task.authorName
                    binding.tvReviewer.text = task.reviewerName
                    binding.tvStatus.text = task.status
                    binding.tvDedline.text = task.finishTime.orEmpty()
                }
            }
        }
    }

    companion object {
        const val TASK_ID = "TASK_ID"
    }
}