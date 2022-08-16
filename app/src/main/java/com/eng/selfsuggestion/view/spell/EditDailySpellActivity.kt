package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityEditDailySpellBinding
import com.eng.selfsuggestion.repository.ArrivedRepository
import com.eng.selfsuggestion.repository.RoutineRepository
import kotlinx.coroutines.CoroutineScope

class EditDailySpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditDailySpellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDailySpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val docId = intent.getStringExtra("docId")
        val content = intent.getStringExtra("content")

        // TODO : 기존 spell name 불러오기
         binding.edittextSpellName.hint = content

        binding.btnSave.setOnClickListener {
            // TODO : Edit (binding.edittextSpellName.text)
            val routineRef = RoutineRepository
            if (docId != null) {
                routineRef.modifyRoutine(mapOf<String,Any>(
                    "content" to binding.edittextSpellName.text.toString()
                ),docId).observe(this,{
                    if (it=="success"){
                        Toast.makeText(this, "success modify spell",
                            Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, "fail modify spell",
                            Toast.LENGTH_SHORT).show()
                    }
                })
            }
            finish()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }


    }
}