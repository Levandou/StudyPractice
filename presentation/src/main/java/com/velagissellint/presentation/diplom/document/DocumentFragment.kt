package com.velagissellint.presentation.diplom.document

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.velagissellint.presentation.databinding.FragmentDocumentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DocumentFragment : Fragment() {
    private val argumentId by lazy {
        arguments?.getInt(DOCUMENT_ID)
    }

    private val viewModel by viewModel<DocumentViewModel>()
    private var _binding: FragmentDocumentBinding? = null
    private val binding: FragmentDocumentBinding
        get() = _binding ?: throw RuntimeException("FragmentDocumentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocumentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        argumentId?.let {
            viewModel.getDocument(it)
        }
        observeDocument()
    }

    private fun observeDocument() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.documentFlow.collectLatest {
                binding.tvDocumentName.text = it?.title.orEmpty()
            }
        }
    }

    companion object {
        const val DOCUMENT_ID = "DOCUMENT_ID"
    }
}