package com.eng.selfsuggestion.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.eng.selfsuggestion.databinding.FragmentSelectDateDialogBinding
import java.util.*

class SelectDateDialogFragment : DialogFragment() {

    private lateinit var _binding: FragmentSelectDateDialogBinding
    private lateinit var listener : DialogSaveClickedListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectDateDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        _binding.btnSave.setOnClickListener {
            val value = Date(_binding.pickerDate.year,_binding.pickerDate.month,_binding.pickerDate.dayOfMonth)
            var strValue = when(_binding.pickerDate.month) {
                1 -> "January"
                2 -> "February"
                3 -> "March"
                4 -> "April"
                5 -> "May"
                6 -> "June"
                7 -> "July"
                8 -> "August"
                9 -> "September"
                10 -> "October"
                11 -> "November"
                12 -> "December"
                else -> " "
            }
            strValue += _binding.pickerDate.dayOfMonth
            strValue += ", " + _binding.pickerDate.year
            Log.i("TAG", "onCreateView: 날짜 다이얼로그"+_binding.pickerDate.year.toString()+value.year)
            listener.onSaveClicked(value, strValue)
            dialog?.dismiss()
        }

        _binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }


        return _binding.root
    }


    fun setOnClickListener(listener: (Date, String) -> Unit) {
        this.listener = object : DialogSaveClickedListener {
            override fun onSaveClicked(content: Date, textValue: String) {
                Log.i("TAG", "onSaveClicked: 클릭했을때 날짜"+content+"/"+textValue)
                listener(content, textValue)
            }
        }
    }

    interface DialogSaveClickedListener {
        fun onSaveClicked(content : Date, textValue : String)
    }
}