package com.velagissellint.presentation.diplom.add_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentAddProfileBinding
import com.velagissellint.presentation.databinding.FragmentAddTaskBinding
import com.velagissellint.presentation.diplom.MainActivity2
import com.velagissellint.presentation.diplom.admin.add_profile.AddProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTaskFragment : Fragment() {
    val layout = R.layout.fragment_add_task

    private val viewModel by viewModel<AddTaskViewModel>()

    private var _binding: FragmentAddTaskBinding? = null
    private val binding: FragmentAddTaskBinding
        get() = _binding ?: throw RuntimeException("FragmentAddTaskBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity2
        viewModel.getAllProfilesFromTeam()

        observeTeamList()
    }

    fun observeTeamList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileListFlow.collectLatest {
                val items = it.map { profile ->
                    profile.fullName
                }.filterNotNull()
                val adapter = ArrayAdapter(requireContext(), R.layout.list_item_team, items)
                binding.assignedAuto.setAdapter(adapter)
                binding.reviewerAuto.setAdapter(adapter)
                if (items.isNotEmpty()){
                    binding.assignedAuto.setText(items.first(), false)
                    binding.reviewerAuto.setText(items.first(), false)
                }
            }
        }
    }
}