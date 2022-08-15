package com.eng.selfsuggestion.view.dialog

import android.content.Intent
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
import com.eng.selfsuggestion.view.spell.EditDailySpellActivity
import com.eng.selfsuggestion.view.spell.EditSpecialSpellActivity
import com.eng.selfsuggestion.view.spell.SpecialSpellFragment

class SpellListFunctionDialogFragment(val fragmentId : Int) : DialogFragment() {

    private lateinit var _binding : FragmentSpellListFunctionDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpellListFunctionDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //  daily : 0, special : 1
        _binding.btnDelete.setOnClickListener {
            when(fragmentId) {
                0 -> {
                    // TODO : daily item 삭제
                    dialog?.dismiss()
                }
                1 -> {
                    // TODO : special item 삭제
                    dialog?.dismiss()
                }
            }
        }

        _binding.btnEdit.setOnClickListener {
            dialog?.dismiss()
            when(fragmentId) {
                0 -> {
                    dialog?.dismiss()
                    val intent = Intent(context,EditDailySpellActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    dialog?.dismiss()
                    val intent = Intent(context,EditSpecialSpellActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        _binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }


        return _binding.root
    }
}