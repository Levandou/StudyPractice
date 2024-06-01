package com.velagissellint.presentation.diplom.admin.add_document

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentAddDocumentBinding
import com.velagissellint.presentation.databinding.FragmentAdminProfileBinding
import com.velagissellint.presentation.diplom.admin.check_profile.AdminProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddDocumentFragment : Fragment() {
    val layout = R.layout.fragment_add_document

    //private val viewModel by viewModel<AdminProfileViewModel>()

    private var _binding: FragmentAddDocumentBinding? = null
    private val binding: FragmentAddDocumentBinding
        get() = _binding ?: throw RuntimeException("FragmentAddDocumentBinding == null")

    private var isCurrentWeekSeenFlag = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDocumentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeProfile()
        observeChart()
    }

    private fun observeProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
  /*          viewModel.profileFlow.collectLatest {
                when (it) {
                    is RequestResult.Success -> {
                        val profile = it.data
                        val textActive = if (profile.isActive) "Работаю"
                        else "Не работаю"

                        binding.tvFullName.text = profile.fullName.orEmpty()
                        binding.tvTeamName.text = profile.teamName
                        binding.tvActive.text = textActive
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
            }*/
        }
    }

    private fun observeChart(){
        viewLifecycleOwner.lifecycleScope.launch {
       /*     viewModel.chartFlow.collectLatest {
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
            }*/
        }
    }

    companion object {
        const val ARGUMENT_PROFILE_ID = "ARGUMENT_PROFILE_ID"
    }
}