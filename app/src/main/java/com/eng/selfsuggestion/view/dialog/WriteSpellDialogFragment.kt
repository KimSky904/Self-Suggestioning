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
import com.eng.selfsuggestion.MainActivity
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.FragmentHomeBinding
import com.eng.selfsuggestion.databinding.FragmentWriteSpellDialogBinding

class WriteSpellDialogFragment : DialogFragment() {

    private lateinit var _binding : FragmentWriteSpellDialogBinding
    private lateinit var bindinghome : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindinghome = FragmentHomeBinding.inflate(inflater, container, false)
        _binding = FragmentWriteSpellDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*
          TODO : 오늘의 Spell FIREBASE 연동
          _binding.txtSpell.text =
          _binding.edittxtSpell.text =
         */

        _binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        _binding.btnAbracadabra.setOnClickListener {
            val textValue : String = _binding.edittxtSpell.text.toString()
            // TODO : "SAMPLE"부분에 FIREBASE 연동
            val dataValue = bindinghome.txtTodaySpell.text.toString()


            if(textValue.uppercase().replace(" ","") == dataValue.uppercase().replace(" ","")) {
                dialog?.dismiss()
            } else {
                // TODO : 일치하지 않는 경우 무슨 처리?
            }
        }

        return _binding.root
    }

}