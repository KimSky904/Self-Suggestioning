package com.eng.selfsuggestion.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.FragmentSpellListFunctionDialogBinding
import com.eng.selfsuggestion.model.RoutineModel
import com.eng.selfsuggestion.view.spell.DailySpellFragment
import com.eng.selfsuggestion.view.spell.SpecialSpellFragment

class SpellListFunctionDialogFragment(val fragmentId : Int) : DialogFragment() {

    private lateinit var _binding : FragmentSpellListFunctionDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpellListFunctionDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        _binding.btnDelete.setOnClickListener {
            when(fragmentId) {
                0 -> {}
                1 -> {}
            }

        }


        return _binding.root
    }
}