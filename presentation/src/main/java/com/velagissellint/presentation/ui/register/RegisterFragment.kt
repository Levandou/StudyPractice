package com.velagissellint.presentation.ui.register

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
import com.google.firebase.firestore.FirebaseFirestore
import com.velagissellint.domain.models.User
import com.velagissellint.presentation.BaseFragment
import com.velagissellint.presentation.ViewModelFactory
import com.velagissellint.presentation.containersDi.ContainerAppContainer
import com.velagissellint.presentation.databinding.FragmentRegisterBinding
import javax.inject.Inject

class RegisterFragment : BaseFragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var navController: NavController

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var registerViewModel: RegisterViewModel

    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterBinding == null")


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as ContainerAppContainer).appContainer()?.plusRegisterComponent()
            ?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        navController = NavHostFragment.findNavController(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnRegister.setOnClickListener {
                if (etEmail.text.toString() != "" && etPassword.text.toString() != "") {
                    registerViewModel.createAccount(
                        etEmail.text.toString(),
                        etPassword.text.toString(), User(
                            etEmail.text.toString(),
                            etFirstName.text.toString(),
                            etPhone.text.toString(),
                            etSecondName.text.toString(),
                            0
                        )
                    )
                    navController.popBackStack()
                } else
                    Toast.makeText(activity, "Поля не должны быть пустыми", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }
}
