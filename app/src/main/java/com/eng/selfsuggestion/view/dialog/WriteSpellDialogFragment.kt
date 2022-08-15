package com.eng.selfsuggestion.view.dialog

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.eng.selfsuggestion.databinding.FragmentWriteSpellDialogBinding
import com.eng.selfsuggestion.model.RoutineModel
import com.eng.selfsuggestion.repository.RoutineRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteSpellDialogFragment : DialogFragment() {

    private lateinit var _binding : FragmentWriteSpellDialogBinding
    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)!!
        _binding = FragmentWriteSpellDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /*
          TODO : 오늘의 Spell FIREBASE 연동
          _binding.txtSpell.text =
          _binding.edittxtSpell.text =
         */
        val docId = pref.getString("todaySpell", "")
        val routineRef = RoutineRepository
        val scopeIO = CoroutineScope(Dispatchers.IO)
        var daily : RoutineModel? = null

        scopeIO.launch {
            daily = docId?.let { routineRef.getRoutine(it) }
        }

        _binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        _binding.btnAbracadabra.setOnClickListener {
            val textValue : String = _binding.edittxtSpell.text.toString()
            // TODO : "SAMPLE"부분에 FIREBASE 연동
            val dataValue = daily?.content

            if(textValue.uppercase().replace(" ","") == dataValue?.uppercase()?.replace(" ","")) {
                // 카운트 증가하기
                scopeIO.launch {
                    routineRef.countUp(daily!!, docId!!)
                }
                dialog?.dismiss()
            } else {
                // TODO : 일치하지 않는 경우 무슨 처리?
            }
        }

        return _binding.root
    }

}