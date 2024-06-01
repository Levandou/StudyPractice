package com.velagissellint.presentation.diplom.profile.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.NumberPickerDialogBinding

class NumberPickerDialog(
    private val numberPickerDialogCallBack: NumberPickerDialogCallBack
) : BottomSheetDialogFragment() {
    interface NumberPickerDialogCallBack{
        fun selectNumber(number: Int)
    }

    private var _binding: NumberPickerDialogBinding? = null
    private val binding: NumberPickerDialogBinding
        get() = _binding ?: throw RuntimeException("NumberPickerDialogBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            NumberPickerDialogBinding.bind(inflater.inflate(R.layout.number_picker_dialog, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 12

        binding.cardSave.setOnClickListener {
            val selectedValue = binding.numberPicker.value

            numberPickerDialogCallBack.selectNumber(selectedValue)
            dialog?.dismiss()
        }
    }
}