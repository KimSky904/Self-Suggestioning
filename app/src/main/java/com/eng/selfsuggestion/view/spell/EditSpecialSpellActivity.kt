package com.eng.selfsuggestion.view.spell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.ActivityEditSpecialSpellBinding
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
            finish()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }

    }
}