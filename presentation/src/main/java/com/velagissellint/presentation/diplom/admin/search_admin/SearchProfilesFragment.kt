package com.velagissellint.presentation.diplom.admin.search_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentSearchProfilesBinding
import com.velagissellint.presentation.diplom.MainActivity2
import com.velagissellint.presentation.diplom.admin.check_profile.AdminProfileFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchProfilesFragment : Fragment() {
    val layout = R.layout.fragment_search_profiles

    private val viewModel by viewModel<SearchProfileViewModel>()

    private var _binding: FragmentSearchProfilesBinding? = null
    private val binding: FragmentSearchProfilesBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchProfilesBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchProfilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.addTextChangedListener {
            val text = it.toString().trim()
            if (text.length < 3)
                return@addTextChangedListener

            viewModel.getProfile(text)
        }

        binding.itemProfile.setOnClickListener {
            val valueProfile = viewModel.profileFlow.value
            if (valueProfile is RequestResult.Success) {
                val bundle = bundleOf(
                    AdminProfileFragment.ARGUMENT_PROFILE_ID to valueProfile.data.id
                )

                (activity as MainActivity2).navigate(R.id.action_searchProfiles_to_adminProfile, bundle)
            }
        }

        observeProfile()
    }

    private fun observeProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileFlow.collectLatest {
                when (it) {
                    is RequestResult.Success -> {
                        val profile = it.data
                        binding.itemProfile.isVisible = profile.id != null

                        if (profile.id == null)
                            return@collectLatest

                        val statusActive = if (profile.isActive) {
                            "Работаю"
                        } else {
                            "Не работаю"
                        }
                        val statusColorRes = if (profile.isActive) {
                            R.color.green_accent
                        } else {
                            R.color.red_accent
                        }

                        binding.tvName.text = profile.fullName.orEmpty()
                        binding.tvActive.text = statusActive
                        binding.tvActive.setTextColor(
                            ContextCompat.getColor(requireContext(), statusColorRes)
                        )
                        binding.tvProfession.text = profile.position.orEmpty()
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
}