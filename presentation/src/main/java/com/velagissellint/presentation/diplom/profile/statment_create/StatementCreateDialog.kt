package com.velagissellint.presentation.diplom.profile.statment_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.StatementCreateDialogBinding

class StatementCreateDialog(
    private val statementCreateDialogCallBack: StatementCreateDialogCallBack
) : BottomSheetDialogFragment() {
    interface StatementCreateDialogCallBack{
        fun onClickSave(newPosition: String, text: String)
    }

    private var _binding: StatementCreateDialogBinding? = null
    private val binding: StatementCreateDialogBinding
        get() = _binding ?: throw RuntimeException("StatementCreateDialogBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            StatementCreateDialogBinding.bind(
                inflater.inflate(
                    R.layout.statement_create_dialog,
                    container
                )
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardSave.setOnClickListener {
            statementCreateDialogCallBack.onClickSave(
                binding.etNewPosition.text.toString(),
                binding.etNewPositionText.text.toString()
            )
            dialog?.dismiss()
        }
    }
}