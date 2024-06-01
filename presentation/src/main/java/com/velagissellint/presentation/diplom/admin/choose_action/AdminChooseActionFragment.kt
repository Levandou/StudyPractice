package com.velagissellint.presentation.diplom.admin.choose_action

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentAdminChooseActionBinding
import com.velagissellint.presentation.diplom.MainActivity2

class AdminChooseActionFragment : Fragment() {
    val layout = R.layout.fragment_admin_choose_action

    private var _binding: FragmentAdminChooseActionBinding? = null
    private val binding: FragmentAdminChooseActionBinding
        get() = _binding ?: throw RuntimeException("FragmentAdminChooseActionBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminChooseActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity2

        binding.cardShowProfile.setOnClickListener {
            mainActivity.navigate(R.id.action_adminChooseAction_to_searchProfiles)
        }

        binding.cardAddEmployee.setOnClickListener {
            mainActivity.navigate(R.id.action_adminChooseAction_to_addProfile)

        }

        binding.cardAddTeam.setOnClickListener {
            mainActivity.navigate(R.id.action_adminChooseAction_to_addTeam)
        }

        binding.cardAddDocument.setOnClickListener {
            mainActivity.navigate(R.id.action_adminChooseActionFragment_to_addDocumentFragment)
        }
    }
}