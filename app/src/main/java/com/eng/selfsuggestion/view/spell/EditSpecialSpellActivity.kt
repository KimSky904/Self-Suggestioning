package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityEditSpecialSpellBinding
import com.eng.selfsuggestion.repository.ArrivedRepository
import com.eng.selfsuggestion.view.dialog.SelectDateDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat

class EditSpecialSpellActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditSpecialSpellBinding
    private lateinit var targetdate : String
    private lateinit var scopeIO : CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSpecialSpellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val docId = intent.getStringExtra("docId")

        binding.txtSpellDate.setOnClickListener {
            val dialogFragment = SelectDateDialogFragment()
            dialogFragment.setOnClickListener { content, textValue ->
                binding.txtSpellDate.text = textValue
                scopeIO = CoroutineScope(Dispatchers.IO)
                content.year = content.year-1900
                targetdate = SimpleDateFormat("yyyy/MM/dd").format(content)
            }
            dialogFragment.show(supportFragmentManager, "SelectDateDialogFragment")
        }

        binding.btnSave.setOnClickListener {
            // TODO : Edit(binding.edittextSpellName.text, ...)
            val arriveRef = ArrivedRepository
            if (docId != null) {
                arriveRef.modifyArrived(mapOf<String,Any>(
                    "content" to binding.edittextSpellName,
                    "arrivedate" to targetdate
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