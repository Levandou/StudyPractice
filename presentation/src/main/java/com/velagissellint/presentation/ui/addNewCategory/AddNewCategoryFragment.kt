package com.velagissellint.presentation.ui.addNewCategory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.velagissellint.presentation.ViewModelFactory
import com.velagissellint.presentation.containersDi.ContainerAppContainer
import com.velagissellint.presentation.databinding.FragmentAddNewCategoryBinding
import javax.inject.Inject

class AddNewCategoryFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var addNewCategoryViewModel: AddNewCategoryViewModel

    private var _binding: FragmentAddNewCategoryBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentAddNewCategoryBinding == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as ContainerAppContainer).appContainer()
            ?.plusAddNewCategoryComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addNewCategoryViewModel =
            ViewModelProvider(this, factory)[AddNewCategoryViewModel::class.java]
        _binding = FragmentAddNewCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAdd.setOnClickListener {
            addNewCategoryViewModel.addNewCategory(binding.etDescription.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}