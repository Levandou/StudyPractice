package com.velagissellint.presentation.diplom.admin.add_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentAddProfileBinding
import com.velagissellint.presentation.databinding.FragmentAddTeamBinding
import com.velagissellint.presentation.diplom.MainActivity2
import com.velagissellint.presentation.diplom.admin.check_profile.AdminProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTeamFragment : Fragment() {
    val layout = R.layout.fragment_add_team

    private val viewModel by viewModel<AddTeamViewModel>()


    private var _binding: FragmentAddTeamBinding? = null
    private val binding: FragmentAddTeamBinding
        get() = _binding ?: throw RuntimeException("FragmentAddTeamBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity2

        binding.cardAddDocument.setOnClickListener {
            viewModel.addTeam(binding.etTeamName.text.toString())
            mainActivity.navigate(R.id.adminProfileFragment, withPopUp = true)
        }
    }
}