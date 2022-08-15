package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import com.eng.selfsuggestion.databinding.ActivityAddSpecialSpellBinding
import com.eng.selfsuggestion.view.dialog.SelectDateDialogFragment
import java.sql.Time
import java.util.*

class AddSpecialSpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddSpecialSpellBinding
    private var timePicker : TimePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpecialSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtSpellDate.setOnClickListener {
            val dialogFragment = SelectDateDialogFragment()
            dialogFragment.setOnClickListener { content, textValue ->
                binding.txtSpellDate.text = textValue
                // TODO : content(현재 자료형 : Date) 형변환 필요
                timePicker = content as TimePicker
            }
            dialogFragment.show(supportFragmentManager, "SelectDateDialogFragment")
        }

        binding.btnSave.setOnClickListener {
            // spell_name : spell name
            // time_picker : 사용자가 입력한 Date, 위에서 형변환 필요
            val spellName = binding.edittextSpellName.text
            if(!spellName.isBlank() && timePicker != null) {
                // TODO : firebase에 저장~!
            }
        }
    }
}