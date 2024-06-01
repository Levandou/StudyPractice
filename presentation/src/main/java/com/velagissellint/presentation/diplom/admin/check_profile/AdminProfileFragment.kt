package com.velagissellint.presentation.diplom.admin.check_profile

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentAdminProfileBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminProfileFragment : Fragment() {
    val layout = R.layout.fragment_admin_profile

    private val profileId by lazy {
        arguments?.getString(ARGUMENT_PROFILE_ID)
            ?: throw Exception("при получении айди получили налл")
    }

    private val viewModel by viewModel<AdminProfileViewModel>()

    private var _binding: FragmentAdminProfileBinding? = null
    private val binding: FragmentAdminProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentAdminProfileBinding == null")

    private var isCurrentWeekSeenFlag = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile(profileId)
        viewModel.getAllTeams()

        binding.tvActive.setOnClickListener {
            if (binding.tvActive.text == "Работает") {
                val colorGreen = ContextCompat.getColor(requireContext(), R.color.green_light)
                binding.tvActive.setTextColor(colorGreen)
                binding.tvActive.text = "Не работает"
            } else {
                val colorRed = ContextCompat.getColor(requireContext(), R.color.red_accent)
                binding.tvActive.setTextColor(colorRed)
                binding.tvActive.text = "Работает"
            }
        }

        binding.btnNextWeek.setOnClickListener {
            binding.btnNextWeek.isVisible = false
            binding.btnLastWeek.isVisible = true

            isCurrentWeekSeenFlag = false
            binding.llHours.isVisible = false
            viewModel.showNextWeek(profileId)

        }

        binding.btnLastWeek.setOnClickListener {
            binding.btnNextWeek.isVisible = true
            binding.btnLastWeek.isVisible = false

            isCurrentWeekSeenFlag
            binding.llHours.isVisible = false
            viewModel.showLastWeek(profileId)
        }

        binding.tvCurrentState.setOnClickListener {

        }

        observeProfile()
        observeChart()
        observeTeam()
        observeTeamList()
    }

    private fun observeProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileFlow.collectLatest {
                when (it) {
                    is RequestResult.Success -> {
                        val profile = it.data
                        val textActive = if (profile.isActive) "Работаю"
                        else "Не работаю"
                        val teamName = profile.teamName.ifEmpty {
                            "Пусто"
                        }

                        binding.teamAuto.setText(teamName, false)
                        binding.tvFullName.setText(profile.fullName.orEmpty())
                        binding.tvCurrentState.setText(profile.position.orEmpty())
                        binding.tvActive.text = textActive
                        profile.teamId?.let {
                            viewModel.getTeam(it)
                        }
                    }

                    is RequestResult.Error -> {
                        val textError = if (it.message.isNotEmpty()) {
                            it.message
                        } else {
                            "Ошибка получения запроса"
                        }
                        Toast.makeText(context, textError, Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeChart() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.chartFlow.collectLatest {
                when (it) {
                    is RequestResult.Success -> {
                        val chart = it.data

                        binding.Mo.text = chart.mo.toString()
                        binding.Tu.text = chart.tu.toString()
                        binding.We.text = chart.we.toString()
                        binding.Th.text = chart.th.toString()
                        binding.Fr.text = chart.fr.toString()
                    }

                    is RequestResult.Error -> {
                        val textError = if (it.message.isNotEmpty()) {
                            it.message
                        } else {
                            "Ошибка получения запроса"
                        }
                        Toast.makeText(context, textError, Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }
            }
        }
    }

    fun observeTeam() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.teamFlow.collectLatest {
                val profileResult = viewModel.profileFlow.value
                if (it is RequestResult.Success && profileResult is RequestResult.Success) {
                    val profile = profileResult.data.copy(teamName = it.data.name.orEmpty())
                    viewModel.profileFlow.emit(RequestResult.Success(profile))
                }
            }
        }
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
                }
            }
        }
    }

    companion object {
        const val ARGUMENT_PROFILE_ID = "ARGUMENT_PROFILE_ID"
    }
}