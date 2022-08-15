package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityEditDailySpellBinding
import kotlinx.coroutines.CoroutineScope

class EditDailySpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditDailySpellBinding
    private lateinit var scopeIO : CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDailySpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO : 기존 spell name 불러오기
        // binding.txtSpellName.text =

        binding.btnSave.setOnClickListener {
            // TODO : Edit (binding.edittextSpellName.text)
            finish()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }


    }
}