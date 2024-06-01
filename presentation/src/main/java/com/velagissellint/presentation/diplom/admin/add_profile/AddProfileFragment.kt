package com.velagissellint.presentation.diplom.admin.add_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentAddProfileBinding
import com.velagissellint.presentation.databinding.FragmentAdminChooseActionBinding
import com.velagissellint.presentation.diplom.MainActivity2
import com.velagissellint.presentation.diplom.admin.check_profile.AdminProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddProfileFragment : Fragment() {
    val layout = R.layout.fragment_add_profile

    private val viewModel by viewModel<AddProfileViewModel>()

    private var _binding: FragmentAddProfileBinding? = null
    private val binding: FragmentAddProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentAddProfileBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity2
        viewModel.getAllTeams()

        observeTeamList()
    }

    fun observeTeamList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.teamListFlow.collectLatest {
                if (it is RequestResult.Success) {
                    val items = it.data.map { team ->
                        team.name
                    }.filterNotNull()
                    val adapter = ArrayAdapter(requireContext(), R.layout.list_item_team, items)
                    binding.teamAuto.setAdapter(adapter)
                    binding.teamAuto.setText(items.first(), false)
                }
            }
        }
    }
}