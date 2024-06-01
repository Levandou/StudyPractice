package com.velagissellint.presentation.diplom.task_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentTaskListBinding
import com.velagissellint.presentation.diplom.MainActivity2
import com.velagissellint.presentation.diplom.task.TaskFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : Fragment() {
    val layout = R.layout.fragment_task_list
    private val viewModel by viewModel<TaskListViewModel>()

    private val adapter by lazy {
        TaskListAdapter { taskId ->
            val bundle = bundleOf(
                TaskFragment.TASK_ID to taskId
            )
            (activity as MainActivity2).navigate(R.id.action_taskList_to_task, bundle)
        }
    }

    private var _binding: FragmentTaskListBinding? = null
    private val binding: FragmentTaskListBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskListBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTasks.adapter = adapter
        viewModel.getTaskLisdasda()
        binding.ivAddTask.setOnClickListener {
            (activity as MainActivity2).navigate(R.id.action_taskListFragment_to_addTaskFragment)
        }
        observeTasks()
        observeTasksWithNames()
    }

    fun observeTasks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.taskListFlow.collectLatest { viewModel.getWithNames(it)
            }
        }
    }

    fun observeTasksWithNames() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.taskListWithNames.collectLatest {
                if (it is RequestResult.Success)
                    adapter.submitList(it.data)
            }
        }
    }
}