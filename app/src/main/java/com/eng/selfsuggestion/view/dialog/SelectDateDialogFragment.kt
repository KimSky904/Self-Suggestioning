package com.eng.selfsuggestion.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.FragmentSelectDateDialogBinding
import com.eng.selfsuggestion.model.SendingModel
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.max

class SelectDateDialogFragment : DialogFragment() {

    private lateinit var _binding : FragmentSelectDateDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectDateDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//        val monthValuesList : List<String> = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
//        setNumberPicker(_binding.pickerMonth, monthValuesList)
//
//        val yearValuesList : MutableList<String> = mutableListOf()
//        for(i in Calendar.YEAR..Calendar.YEAR + 5) {
//            yearValuesList += i.toString()
//        }
//        setNumberPicker(_binding.pickerYear, yearValuesList)
//
//        val max = getMaxDateInMonth(Calendar.MONTH, Calendar.YEAR)
//        val dateValuesList : MutableList<String> = mutableListOf()
//        for(i in 1..max) {
//            dateValuesList += i.toString()
//        }
//        setNumberPicker(_binding.pickerDate, dateValuesList)


        _binding.btnSave.setOnClickListener {
            _binding.pickerDate.year
            dialog?.dismiss()
        }
        _binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

//        _binding.pickerMonth.setOnValueChangedListener { _, _, newVal ->
//            val maxValue = getMaxDateInMonth(newVal, _binding.pickerYear.value)
//            if(_binding.pickerDate.value > maxValue) {
//                _binding.pickerDate.displayedValues = null
//                _binding.pickerDate.maxValue = maxValue
//            }
//            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
//        }
//        _binding.pickerYear.setOnValueChangedListener { _, _, newVal ->
//            val maxValue = getMaxDateInMonth(_binding.pickerMonth.value, newVal)
//            if(_binding.pickerDate.value > maxValue) {
//                _binding.pickerDate.displayedValues = null
//                _binding.pickerDate.maxValue = maxValue
//            }
//            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
//        }



        return _binding.root
    }
//    private fun getMaxDateInMonth(month : Int, year : Int) : Int {
//        return when(month-1) {
//            Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
//            Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
//            Calendar.FEBRUARY -> if(year%4 == 0 && year % 100 != 0 || year % 400 == 0) 29 else 28
//            else -> throw IllegalArgumentException("Month is Wrong")
//        }
//    }
//
//    private fun setNumberPicker(numberPicker : NumberPicker, valuesList: List<String>) {
//        numberPicker.maxValue = valuesList.size
//        numberPicker.minValue = 1
//        numberPicker.showDividers = NumberPicker.SHOW_DIVIDER_NONE
//        numberPicker.wrapSelectorWheel = true
//        numberPicker.displayedValues = valuesList.toTypedArray()
//    }
}