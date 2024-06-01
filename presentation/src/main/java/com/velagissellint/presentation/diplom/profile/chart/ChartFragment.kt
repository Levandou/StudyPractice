package com.velagissellint.presentation.diplom.profile.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.FragmentChartBinding
import com.velagissellint.presentation.diplom.MainActivity2
import com.velagissellint.presentation.diplom.profile.ProfileFragment

class ChartFragment : Fragment() {
    val layout = R.layout.fragment_chart

    private var _binding: FragmentChartBinding? = null
    private val binding: FragmentChartBinding
        get() = _binding ?: throw RuntimeException("FragmentChartBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdvice.setOnClickListener {
            val hourCount = binding.etHourCount.text.toString().toIntOrNull() ?: 0
            val mod = hourCount % 5

            val hourMo = if (mod != 0) {
                hourCount / 5 + 1
            } else hourCount / 5
            binding.tvMo.hint = "$hourMo"

            val hourTu = if (mod > 1) {
                hourCount / 5 + 1
            } else hourCount / 5
            binding.tvTu.hint = "$hourTu"

            val hourWe = if (mod > 2) {
                hourCount / 5 + 1
            } else hourCount / 5
            binding.tvWe.hint = "$hourWe"

            val hourTh = if (mod > 3) {
                hourCount / 5 + 1
            } else hourCount / 5
            binding.tvTh.hint = "$hourTh"

            binding.tvFr.hint = "${hourCount / 5}"
        }

        binding.flMo.setOnClickListener {
            NumberPickerDialog(
                object : NumberPickerDialog.NumberPickerDialogCallBack {
                    override fun selectNumber(number: Int) {
                        binding.tvMo.setText(number.toString())
                        changeHint()
                    }
                }
            ).show(childFragmentManager, "tag")
        }

        binding.flTu.setOnClickListener {
            NumberPickerDialog(
                object : NumberPickerDialog.NumberPickerDialogCallBack {
                    override fun selectNumber(number: Int) {
                        binding.tvTu.setText(number.toString())
                        changeHint()
                    }
                }
            ).show(requireActivity().supportFragmentManager, "tag")
        }

        binding.flWe.setOnClickListener {
            NumberPickerDialog(
                object : NumberPickerDialog.NumberPickerDialogCallBack {
                    override fun selectNumber(number: Int) {
                        binding.tvWe.setText(number.toString())
                        changeHint()
                    }
                }
            ).show(requireActivity().supportFragmentManager, "tag")
        }

        binding.flTh.setOnClickListener {
            NumberPickerDialog(
                object : NumberPickerDialog.NumberPickerDialogCallBack {
                    override fun selectNumber(number: Int) {
                        binding.tvTh.setText(number.toString())
                        changeHint()
                    }
                }
            ).show(requireActivity().supportFragmentManager, "tag")
        }

        binding.flFr.setOnClickListener {
            NumberPickerDialog(
                object : NumberPickerDialog.NumberPickerDialogCallBack {
                    override fun selectNumber(number: Int) {
                        binding.tvFr.setText(number.toString())
                        changeHint()
                    }
                }
            ).show(requireActivity().supportFragmentManager, "tag")
        }

        binding.cardSave.setOnClickListener {
            val text1 = binding.tvMo.text.toString().toIntOrNull() ?: return@setOnClickListener
            val text2 = binding.tvTu.text.toString().toIntOrNull() ?: return@setOnClickListener
            val text3 = binding.tvWe.text.toString().toIntOrNull() ?: return@setOnClickListener
            val text4 = binding.tvTh.text.toString().toIntOrNull() ?: return@setOnClickListener
            val text5 = binding.tvFr.text.toString().toIntOrNull() ?: return@setOnClickListener

            val bundle = bundleOf(
                ProfileFragment.ARGUMENT_MO to text1,
                ProfileFragment.ARGUMENT_TU to text2,
                ProfileFragment.ARGUMENT_WE to text3,
                ProfileFragment.ARGUMENT_TH to text4,
                ProfileFragment.ARGUMENT_FR to text5,
            )
            (activity as MainActivity2).navigate(R.id.profileFragment, bundle)
        }
    }

    fun changeHint() {
        val text1 = binding.tvMo.text.toString().toIntOrNull() ?: 0
        val text2 = binding.tvTu.text.toString().toIntOrNull() ?: 0
        val text3 = binding.tvWe.text.toString().toIntOrNull() ?: 0
        val text4 = binding.tvTh.text.toString().toIntOrNull() ?: 0
        val text5 = binding.tvFr.text.toString().toIntOrNull() ?: 0
        val totalSelectedCount = text1 + text2 + text3 + text4 + text5
        val hourCount = binding.etHourCount.text.toString().toIntOrNull() ?: 0
        val modCount = hourCount - totalSelectedCount

        val listDate = listOf(binding.tvMo, binding.tvTu, binding.tvWe, binding.tvTh, binding.tvFr)
        val countNotSelected = listOf(text1, text2, text3, text4, text5).count {
            it == 0
        }

        val hintCount = if (modCount > 0 && countNotSelected != 0) {
            modCount / countNotSelected
        } else 0

        listDate.forEach {
            it.hint = hintCount.toString()
        }
    }
}