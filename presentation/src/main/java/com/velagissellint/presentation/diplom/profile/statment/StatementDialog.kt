package com.velagissellint.presentation.diplom.profile.statment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentProfileBinding
import com.velagissellint.presentation.databinding.StatementDialogBinding

class StatementDialog(
    private val newPosition: String,
    private val textStatement: String
) : BottomSheetDialogFragment() {
    private var _binding: StatementDialogBinding? = null
    private val binding: StatementDialogBinding
        get() = _binding ?: throw RuntimeException("StatementDialogBinding == null")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = StatementDialogBinding.bind(inflater.inflate(R.layout.statement_dialog, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNewLevel.text = "Новый уровень: $newPosition"
        binding.tvTextStatement.text = textStatement
    }
}