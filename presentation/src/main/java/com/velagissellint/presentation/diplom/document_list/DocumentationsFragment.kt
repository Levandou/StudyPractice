package com.velagissellint.presentation.diplom.document_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentDocumentationBinding
import com.velagissellint.presentation.diplom.MainActivity2
import com.velagissellint.presentation.diplom.document.DocumentFragment
import com.velagissellint.presentation.diplom.profile.ProfileViewModel
import com.velagissellint.presentation.diplom.task.TaskFragment
import com.velagissellint.presentation.diplom.task_list.TaskListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DocumentationsFragment : Fragment() {

    private val viewModel by viewModel<DocumentationsViewModel>()

    private val adapter by lazy {
        DocumentListAdapter { documentId ->
            val bundle = bundleOf(
                DocumentFragment.DOCUMENT_ID to documentId
            )
            (activity as MainActivity2).navigate(R.id.action_documentationFragment_to_documentFragment, bundle)
        }
    }

    private var _binding: FragmentDocumentationBinding? = null
    private val binding: FragmentDocumentationBinding
        get() = _binding ?: throw RuntimeException("FragmentDocumentationBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocumentationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDocumentList()
        binding.rvDocuments.adapter = adapter
        observeDocumentList()
    }

    fun observeDocumentList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.documentListFlow.collectLatest {
                adapter.submitList(it)
            }
        }
    }
}