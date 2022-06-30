package com.velagissellint.presentation.ui.logIn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.velagissellint.presentation.BaseFragment
import com.velagissellint.presentation.R
import com.velagissellint.presentation.ViewModelFactory
import com.velagissellint.presentation.containersDi.ContainerAppContainer
import com.velagissellint.presentation.databinding.FragmentLogInBinding
import javax.inject.Inject

class LogInFragment : BaseFragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var logInViewModel: LogInViewModel

    private var _binding: FragmentLogInBinding? = null
    private val binding: FragmentLogInBinding
        get() = _binding ?: throw RuntimeException("FragmentLogInBinding == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as ContainerAppContainer).appContainer()?.plusLogInComponent()
            ?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        logInViewModel = ViewModelProvider(this, factory).get(LogInViewModel::class.java)
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        navController = NavHostFragment.findNavController(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                if (etEmail.text.toString() != "" && etPassword.text.toString() != "")
                    logInViewModel.logIn(etEmail.text.toString(), etPassword.text.toString())
                else
                    Toast.makeText(activity, "Поля не должны быть пустыми", Toast.LENGTH_SHORT)
                        .show()
                navController.navigate(R.id.categories_list_fragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}