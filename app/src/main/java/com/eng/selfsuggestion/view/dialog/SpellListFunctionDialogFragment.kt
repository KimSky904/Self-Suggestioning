package com.eng.selfsuggestion.view.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.eng.selfsuggestion.databinding.FragmentSpellListFunctionDialogBinding
import com.eng.selfsuggestion.model.ArrivedModel
import com.eng.selfsuggestion.model.RoutineModel
import com.eng.selfsuggestion.model.SendingModel
import com.eng.selfsuggestion.repository.ArrivedRepository
import com.eng.selfsuggestion.repository.RoutineRepository
import com.eng.selfsuggestion.view.spell.EditDailySpellActivity
import com.eng.selfsuggestion.view.spell.EditSpecialSpellActivity

class SpellListFunctionDialogFragment(val fragmentId: Int) : DialogFragment() {

    private lateinit var _binding : FragmentSpellListFunctionDialogBinding
    var daily : RoutineModel? = null
    var special : ArrivedModel? = null

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
                    val routineRef = RoutineRepository
                    daily?.docId?.let { it1 ->
                        routineRef.deleteRoutine(it1).observe(requireActivity(),{
                            if (it=="success"){
                                Toast.makeText(context, "success delete spell",
                                    Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, "fail delete spell",
                                    Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    dialog?.dismiss()
                }
                1 -> {
                    // TODO : special item 삭제
                    val arriveRef = ArrivedRepository
                    special?.docId?.let { it1 ->
                        arriveRef.deleteArrived(it1).observe(requireActivity(),{
                            if (it=="success"){
                                Toast.makeText(context, "success delete spell",
                                    Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, "fail delete spell",
                                    Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
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

    @JvmName("setDaily1")
    fun setDaily(item : RoutineModel){
        daily = item
    }

    fun setArrived(item : ArrivedModel){
        special = item
    }
}