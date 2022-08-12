package com.eng.selfsuggestion.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.FragmentWriteSpellDialogBinding

class WriteSpellDialogFragment : DialogFragment() {

    private lateinit var _binding : FragmentWriteSpellDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWriteSpellDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // TODO : 오늘의 Spell 연동
        // _binding.txtSpell.text =
        // _binding.edittxtSpell.text =

        _binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        _binding.btnAbracadabra.setOnClickListener {
            dialog?.dismiss()
        }


        return _binding.root
    }

}