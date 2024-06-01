package com.velagissellint.presentation.diplom.logIn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.velagissellint.domain.models.User
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentLogInBinding
import com.velagissellint.presentation.diplom.MainActivity2
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private val logInViewModel by viewModel<LogInViewModel>()

    private var _binding: FragmentLogInBinding? = null
    private val binding: FragmentLogInBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInBinding == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*   (activity?.application as ContainerAppContainer).appContainer()?.plusLogInComponent()
               ?.inject(this)*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        navController = NavHostFragment.findNavController(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            logInViewModel.loginResult.collectLatest {
                when (it) {
                    is RequestResult.Success -> {
                        Toast.makeText(
                            context,
                            "Вход прошёл успешно",
                            Toast.LENGTH_LONG
                        ).show()
                        val email = binding.etEmail.text.toString()
                        logInViewModel.saveUser(User(email, email, false))
                        logInViewModel.loadIsAdmin(email)
                    }

                    is RequestResult.Error -> {
                        val textError = if (it.message.isNotEmpty()) {
                            it.message
                        } else {
                            "Ошибка входа"
                        }
                        Toast.makeText(
                            context,
                            textError,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {}
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            logInViewModel.isAdminResult.collectLatest {
                when (it) {
                    is RequestResult.Success -> {
                        val user = logInViewModel.user?.copy(isAdmin = it.data)
                        user?.let { it1 ->
                            logInViewModel.updateUser(it1)
                        }
                        if (it.data) {
                            (activity as MainActivity2).navigate(R.id.action_logIn_to_adminChooseAction)
                        } else {
                            (activity as MainActivity2).navigate(R.id.action_logIn_to_profile)
                        }
                    }

                    is RequestResult.Error -> {
                        val textError = if (it.message.isNotEmpty()) {
                            it.message
                        } else {
                            "Ошибка входа"
                        }
                        Toast.makeText(
                            context,
                            textError,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {}
                }
            }
        }

        binding.apply {
            btnLogin.setOnClickListener {
                if (etEmail.text.toString() != "" && etPassword.text.toString() != "") {
                    logInViewModel.logIn(etEmail.text.toString(), etPassword.text.toString())
                } else {
                    Toast.makeText(
                        activity,
                        "Поля не должны быть пустыми",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}