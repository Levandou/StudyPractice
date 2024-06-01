package com.velagissellint.presentation.diplom.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentProfileBinding
import com.velagissellint.presentation.diplom.MainActivity2
import com.velagissellint.presentation.diplom.profile.statment.StatementDialog
import com.velagissellint.presentation.diplom.profile.statment_create.StatementCreateDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    val layout = R.layout.fragment_profile

    private val viewModel by viewModel<ProfileViewModel>()

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        saveChartArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStatement()
        viewModel.getProfile()

        binding.tvActive.setOnClickListener {
            if (binding.tvActive.text == "Работаю") {
                val colorRed = ContextCompat.getColor(requireContext(), R.color.red_accent)
                binding.tvActive.setTextColor(colorRed)
                binding.tvActive.text = "Не работаю"
            } else {
                val colorGreen = ContextCompat.getColor(requireContext(), R.color.green_light)
                binding.tvActive.setTextColor(colorGreen)
                binding.tvActive.text = "Работаю"
            }
        }

        binding.cardStatement.setOnClickListener {
            val statementRequest = viewModel.statementFlow.value

            if (statementRequest is RequestResult.Success && statementRequest.data.id != null) {
                StatementDialog(
                    newPosition = statementRequest.data.newPosition.orEmpty(),
                    textStatement = statementRequest.data.text.orEmpty()
                ).show(requireActivity().supportFragmentManager, "tag")
            } else {
                StatementCreateDialog(object : StatementCreateDialog.StatementCreateDialogCallBack {
                    override fun onClickSave(newPosition: String, text: String) {
                        if (newPosition.isNotEmpty() && text.isNotEmpty())
                            viewModel.saveStatement(newPosition, text)
                        else {
                            Toast.makeText(context, "Пустой уровень или текст", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
                ).show(requireActivity().supportFragmentManager, "tag")
            }
        }

        binding.btnSelectChart.setOnClickListener {
            (activity as MainActivity2).navigate(R.id.action_profileFragment_to_chartFragment)
        }

        observeStatement()
        observeProfile()
    }

    fun observeStatement() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.statementFlow.collectLatest {
                when (it) {
                    is RequestResult.Success -> {
                        val textStatement = if (it.data.id != null)
                            "Посмотреть запрос"
                        else "Запросить пересмотр уровня"
                        binding.tvPosition.text = textStatement
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

    fun observeProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileFlow.collectLatest {
                when (it) {
                    is RequestResult.Success -> {
                        val profile = it.data
                        val textActive = if (profile.isActive) "Работаю"
                        else "Не работаю"

                        binding.tvFullName.text = profile.fullName.orEmpty()
                        binding.tvCurrentState.text = "Должность: ${profile.position.orEmpty()}"
                        //binding.tvPosition.text = profile.position.orEmpty()
                       // binding.tvTeamName.text = profile.teamName
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
            }
        }
    }

    private fun saveChartArguments(){
        val argumentMo = arguments?.getInt("ARGUMENT_MO") ?: return
        val argumentTu = arguments?.getInt("ARGUMENT_TU") ?: return
        val argumentWe = arguments?.getInt("ARGUMENT_WE") ?: return
        val argumentTh = arguments?.getInt("ARGUMENT_TH") ?: return
        val argumentFr = arguments?.getInt("ARGUMENT_FR") ?: return

        viewModel.saveChart(argumentMo, argumentTu, argumentWe, argumentTh, argumentFr)
    }

    companion object {
        const val ARGUMENT_MO = "ARGUMENT_MO"
        const val ARGUMENT_TU = "ARGUMENT_TU"
        const val ARGUMENT_WE = "ARGUMENT_WE"
        const val ARGUMENT_TH = "ARGUMENT_TH"
        const val ARGUMENT_FR = "ARGUMENT_FR"
    }
}